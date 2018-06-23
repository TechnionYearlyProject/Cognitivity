import { Component, OnInit, OnChanges } from '@angular/core';
import { ImageService } from '../../services/image/image.service';
import { GalleryImage } from '../../models/galleryImage/galleryImage.model';
import { Observable } from 'rxjs/Observable';
import { PictureLinkService } from '../../services/database-service'
import { forEach } from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit, OnChanges {
  images: Observable<String[]>;
  base: string= 'https://firebasestorage.googleapis.com/v0/b/cognitivity-f042d.appspot.com/o/uploads%2';

  constructor(private pictureLinkService: PictureLinkService) { }

  ngOnInit() {
    this.images = Observable.fromPromise(this.pictureLinkService.findAllPictureLinks());
  }

  ngOnChanges() {
    this.images = Observable.fromPromise(this.pictureLinkService.findAllPictureLinks());
  }
}