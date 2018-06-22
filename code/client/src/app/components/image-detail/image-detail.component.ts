import { Component, OnInit, OnChanges } from '@angular/core';
import { ImageService } from '../../services/image/image.service';
import { ActivatedRoute } from '@angular/router';
import { GalleryImage } from '../../models/galleryImage/galleryImage.model';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-image-detail',
  templateUrl: './image-detail.component.html',
  styleUrls: ['./image-detail.component.css']
})
export class ImageDetailComponent implements OnInit {
  imageUrl = '';

  constructor(private imageService: ImageService,
    private route: ActivatedRoute) { }

  getImageUrl(key: string) {
    this.imageService.getImage(key)
      .then(image => this.imageUrl = image.url);
  }

  ngOnInit() {
    this.getImageUrl(this.route.snapshot.params['id']);
  }
}