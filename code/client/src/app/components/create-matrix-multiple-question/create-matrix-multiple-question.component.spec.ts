import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMatrixMultipleQuestionComponent } from './create-matrix-multiple-question.component';

describe('CreateMatrixMultipleQuestionComponent', () => {
  let component: CreateMatrixMultipleQuestionComponent;
  let fixture: ComponentFixture<CreateMatrixMultipleQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateMatrixMultipleQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateMatrixMultipleQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
