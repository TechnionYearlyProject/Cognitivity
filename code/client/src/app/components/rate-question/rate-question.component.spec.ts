import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RateQuestionComponent } from './rate-question.component';

describe('RateQuestionComponent', () => {
  let component: RateQuestionComponent;
  let fixture: ComponentFixture<RateQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RateQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RateQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
