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

  constructor(private ngFire: AngularFireModule, private db: AngularFireDatabase, private pictureLinkService: PictureLinkService) { }

  uploadFile(upload: Upload) {
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
        console.log(upload.progress);
      },
      // 2.) error observer
      (error) => {
        // upload failed
        console.log(error);
      },
      // 3.) success observer
      (): any => {
        upload.url = uploadTask.snapshot.downloadURL;
        upload.name = upload.file.name;
        this.saveFileData(uploadTask.snapshot.downloadURL);
      }
    );
  }
  //Saving the link in the database
  private async saveFileData(upload: String) {
    await this.pictureLinkService.savePictureLink(upload);
    console.log('File saved!: ' + upload);
  }
}