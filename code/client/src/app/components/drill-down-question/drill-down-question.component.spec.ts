import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DrillDownQuestionComponent } from './drill-down-question.component';

describe('DrillDownQuestionComponent', () => {
  let component: DrillDownQuestionComponent;
  let fixture: ComponentFixture<DrillDownQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DrillDownQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DrillDownQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
