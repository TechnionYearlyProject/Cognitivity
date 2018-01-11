import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { registrationFormComponent } from './registration-form.component';

describe('registrationFormComponent', () => {
  let component: registrationFormComponent;
  let fixture: ComponentFixture<registrationFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ registrationFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(registrationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});