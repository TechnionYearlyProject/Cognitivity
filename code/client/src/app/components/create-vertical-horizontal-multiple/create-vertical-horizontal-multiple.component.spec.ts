import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateVerticalHorizontalMultipleComponent } from './create-vertical-horizontal-multiple.component';

describe('CreateVerticalHorizontalMultipleComponent', () => {
  let component: CreateVerticalHorizontalMultipleComponent;
  let fixture: ComponentFixture<CreateVerticalHorizontalMultipleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateVerticalHorizontalMultipleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateVerticalHorizontalMultipleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
