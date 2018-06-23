import { Component, OnInit, Output, EventEmitter } from '@angular/core';
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

  // Output event that emits the time took answering the question
  @Output() finished: EventEmitter<any> = new EventEmitter();

  constructor(private uploadService: UploadService) {}

  ngOnInit(){
  }

  handleFiles(event) {
    this.files = event.target.files;
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
  }
}
