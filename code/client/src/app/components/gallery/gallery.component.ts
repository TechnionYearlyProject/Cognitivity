import { Component, OnInit, OnChanges } from '@angular/core';
import { ImageService } from '../../services/image/image.service';
import { GalleryImage } from '../../models/galleryImage/galleryImage.model';
import { Observable } from 'rxjs/Observable';
import { PictureLinkService } from '../../services/database-service'

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit, OnChanges {
  images: Observable<String[]>;

  constructor(private pictureLinkService: PictureLinkService) { }

  ngOnInit() {
    this.images = Observable.fromPromise(this.pictureLinkService.findAllPictureLinks());
  }

  ngOnChanges() {
    this.images = Observable.fromPromise(this.pictureLinkService.findAllPictureLinks());
  }
}