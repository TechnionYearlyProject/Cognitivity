import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateOpenQuestionComponent } from './create-open-question.component';

describe('CreateOpenQuestionComponent', () => {
  let component: CreateOpenQuestionComponent;
  let fixture: ComponentFixture<CreateOpenQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateOpenQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateOpenQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
