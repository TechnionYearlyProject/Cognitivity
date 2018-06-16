import {Injectable} from '@angular/core';
import {AngularFireDatabase} from 'angularfire2/database';
import * as firebase from 'firebase';
 
import {Upload} from '../../components/uploads/upload-form/upload';
 
@Injectable()
export class UploadService {
 
  constructor(private db: AngularFireDatabase) {}
 
  private basePath = '/uploads';
 
  pushFileToStorage(fileUpload: Upload, progress: {percentage: number}) {
    const storageRef = firebase.storage().ref();
    const uploadTask = storageRef.child(`${this.basePath}/${fileUpload.file.name}`).put(fileUpload.file);
 
    uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED,
      (snapshot) => {
        // in progress
        const snap = snapshot as firebase.storage.UploadTaskSnapshot
        progress.percentage = Math.round((snap.bytesTransferred / snap.totalBytes) * 100)
      },
      (error) => {
        // fail
        alert("Could no upload file.\nPlease try again later.");
        console.log(error)
      },
      () => {
        // success
        fileUpload.url = uploadTask.snapshot.downloadURL
        fileUpload.name = fileUpload.file.name
        this.saveFileData(fileUpload)
      }
    );
  }

  deleteUpload(upload: Upload) {
    this.deleteFileData(upload.$key)
    .then( () => {
      this.deleteFileStorage(upload.name)
    })
    .catch(error => console.log(error))
  }

    // Deletes the file details from the realtime db
    private deleteFileData(key: string) {
      return this.db.list(`${this.basePath}/`).remove(key);
    }

      // Firebase files must have unique names in their respective storage dir
  // So the name serves as a unique key
  private deleteFileStorage(name:string) {
    let storageRef = firebase.storage().ref();
    storageRef.child(`${this.basePath}/${name}`).delete()
  }
 
  private saveFileData(fileUpload: Upload) {
    this.db.list(`${this.basePath}/`).push(fileUpload);
  }
}