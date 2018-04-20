import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateDrillDownQuestionComponent } from './create-drill-down-question.component';

describe('CreateDrillDownQuestionComponent', () => {
  let component: CreateDrillDownQuestionComponent;
  let fixture: ComponentFixture<CreateDrillDownQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateDrillDownQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateDrillDownQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
