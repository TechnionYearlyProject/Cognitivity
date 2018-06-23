import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PicturePreviewComponent } from './picture-preview.component';

describe('PicturePreviewComponent', () => {
  let component: PicturePreviewComponent;
  let fixture: ComponentFixture<PicturePreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PicturePreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PicturePreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
