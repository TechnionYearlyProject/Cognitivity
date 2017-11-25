import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MultipleQuestionComponent } from './multiple-question.component';

describe('MultipleQuestionComponent', () => {
  let component: MultipleQuestionComponent;
  let fixture: ComponentFixture<MultipleQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MultipleQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MultipleQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
