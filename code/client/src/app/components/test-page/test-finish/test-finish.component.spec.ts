import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestFinishComponent } from './test-finish.component';

describe('TestFinishComponent', () => {
  let component: TestFinishComponent;
  let fixture: ComponentFixture<TestFinishComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestFinishComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestFinishComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
