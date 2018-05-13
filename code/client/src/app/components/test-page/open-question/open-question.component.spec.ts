import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenQuestionComponent } from './open-question.component';

describe('OpenQuestionComponent', () => {
  let component: OpenQuestionComponent;
  let fixture: ComponentFixture<OpenQuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpenQuestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpenQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
