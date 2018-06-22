import { Component, OnInit } from '@angular/core';
import { UploadService } from '../../services/uploads/upload.service';
import { Upload } from './upload';
import * as _ from 'lodash'; 
import { FirebaseApp } from 'angularfire2';

@Component({
  selector: 'app-upload',
  templateUrl: './Upload.component.html',
  styleUrls: ['./Upload.component.css']
})
export class UploadComponent {

  files: FileList;
  upload: Upload;

  constructor(private uploadService: UploadService) {}

  handleFiles(event) {
    this.files = event.target.files;
  }

  uploadFiles() {
    const filesToUpload = this.files;
    const filesIdx = _.range(filesToUpload.length);
    _.each(filesIdx, (idx) => {
      // console.log(filesToUpload[idx]);
      this.upload = new Upload(filesToUpload[idx]);
      console.log("Uplodaing another");
      this.uploadService.uploadFile(this.upload);
    });
  }
}