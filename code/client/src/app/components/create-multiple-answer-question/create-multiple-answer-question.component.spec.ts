import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMultipleAnswerQuestionComponent } from './create-multiple-answer-question.component';

describe('CreateMultipleAnswerQuestionComponent', () => {
  let component: CreateMultipleAnswerQuestionComponent;
  let fixture: ComponentFixture<CreateMultipleAnswerQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateMultipleAnswerQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateMultipleAnswerQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
