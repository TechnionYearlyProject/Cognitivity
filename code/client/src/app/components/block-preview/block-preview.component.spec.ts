import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BlockPreviewComponent } from './block-preview.component';

describe('BlockPreviewComponent', () => {
  let component: BlockPreviewComponent;
  let fixture: ComponentFixture<BlockPreviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BlockPreviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BlockPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
