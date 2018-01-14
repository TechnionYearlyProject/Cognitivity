import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionViewerComponent } from './question-viewer.component';

describe('QuestionViewerComponent', () => {
  let component: QuestionViewerComponent;
  let fixture: ComponentFixture<QuestionViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestionViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
