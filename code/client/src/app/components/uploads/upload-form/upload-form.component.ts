import {Component, OnInit} from '@angular/core';
 
import {UploadService} from '../../../services/uploads/upload.service';
import {Upload} from './upload';
 
@Component({
  selector: 'upload-form',
  templateUrl: './upload-form.component.html',
  styleUrls: ['./upload-form.component.css']
})
export class FormUploadComponent implements OnInit {
 
  selectedFiles: FileList
  currentFileUpload: Upload
  progress: {percentage: number} = {percentage: 0}
 
  constructor(private uploadService: UploadService) {}
 
  ngOnInit() {
  }
 
  selectFile(event) {
    this.selectedFiles = event.target.files;
  }
 
  upload() {
    const file = this.selectedFiles.item(0)
    this.currentFileUpload = new Upload(file);
    this.uploadService.pushFileToStorage(this.currentFileUpload, this.progress)
  }
}