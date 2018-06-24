/*
Author: Pe'er Sagiv
Date: 16.6.18
A class to handle all the pictrue uploads to the system
*/

import { Component, OnInit, Output, EventEmitter, ViewChild } from '@angular/core';
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
  noChoosenImages: boolean = true;
  // Output event that emits the time took answering the question
  @Output() finished: EventEmitter<any> = new EventEmitter();


  @ViewChild('filesInput')
  filesComponent: any;

  constructor(private uploadService: UploadService) {}

  ngOnInit(){
  }

  handleFiles(event) {
      if(event.target.files.length != 0){
          this.files = event.target.files;
          this.noChoosenImages = false;
      }
  }

  uploadFiles() {
    let url : any;
    const filesToUpload = this.files;
    const filesIdx = _.range(filesToUpload.length);
    _.each(filesIdx, (idx) => {
      this.upload = new Upload(filesToUpload[idx]);
      url = this.uploadService.uploadFile(this.upload,(upload) => {
            this.finished.emit(upload.url);
      });
    });
    this.filesComponent.nativeElement.value="";
    this.noChoosenImages = true;
    setTimeout(() => this.upload = null, 3000)
  }
}
