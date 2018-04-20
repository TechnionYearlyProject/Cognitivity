import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateRateQuestionComponent } from './create-rate-question.component';

describe('CreateRateQuestionComponent', () => {
  let component: CreateRateQuestionComponent;
  let fixture: ComponentFixture<CreateRateQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateRateQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateRateQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
