import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviewImportBlockComponent } from './preview-import-block.component';

describe('PreviewImportBlockComponent', () => {
  let component: PreviewImportBlockComponent;
  let fixture: ComponentFixture<PreviewImportBlockComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviewImportBlockComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviewImportBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
