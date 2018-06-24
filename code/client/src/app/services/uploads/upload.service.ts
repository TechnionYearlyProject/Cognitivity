/*
Author: Pe'er Sagiv
Date: 6.6.18
*/

import { Injectable } from '@angular/core';
import { AngularFireModule } from 'angularfire2';
import { GalleryImage } from '../../models/galleryImage/galleryImage.model';
import { AngularFireDatabase } from 'angularfire2/database';
import { FirebaseListObservable, FirebaseObjectObservable } from  'angularfire2/database-deprecated'
import { Upload } from '../../components/uploads/upload';
import { PictureLinkService } from '../database-service'
import * as  firebase from 'firebase';


@Injectable()
export class UploadService {

  private basePath = '/uploads';
  private uploads: FirebaseListObservable<GalleryImage[]>;

  constructor(private ngFire: AngularFireModule,
            private db: AngularFireDatabase,
            private pictureLinkService: PictureLinkService) { }

  uploadFile(upload: Upload, callback) {
    //firebase.initializeApp(environment.firebase,'Cognitivity1');
    const storageRef = firebase.storage().ref();
    const uploadTask = storageRef.child(`${this.basePath}/${upload.file.name}`)
      .put(upload.file);

    uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED,
      // three observers
      // 1.) state_changed observer
      (snapshot) => {
        // upload in progress
        upload.progress = (uploadTask.snapshot.bytesTransferred / uploadTask.snapshot.totalBytes) * 100;
      },
      // 2.) error observer
      (error) => {
        // upload failed
        console.log(error);
      },
      // 3.) success observer
      () => {
            upload.url = uploadTask.snapshot.downloadURL;
            upload.name = upload.file.name;
            this.saveFileData(upload);
            callback(upload);
      });
  }
  //Saving the link in the database
  private async saveFileData(upload: Upload) {
    await this.pictureLinkService.savePictureLink(upload.url,upload.name);
  }

  deleteFileUpload(upload: string) {
    this.deleteFileDatabase(upload)
      .then(() => {
        this.deleteFileStorage(upload);
      })
      .catch(error => console.log(error));
  }


  private deleteFileDatabase(name: string) {
    return this.pictureLinkService.deletePictureLink(name);
  }

  private deleteFileStorage(name: string) {
    const storageRef = firebase.storage().ref();
    storageRef.child(`${this.basePath}/${name}`).delete();
  }
}
