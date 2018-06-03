import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { SubjectService } from '../../services/database-service/index';
import { TestSubject } from '../../models/index';


@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})

/*
This component represents the registration form for the subject.
*/
export class registrationFormComponent implements OnInit {
  //defines the subjects field that we'll get from the form.
  user : TestSubject = {
    name:'',
    occupation :'',
    birthDate:'',
    martialStatus:''
  }

  @Output() complete: EventEmitter<TestSubject> = new EventEmitter();

  //default constructor.
  constructor(
                private subjectService : SubjectService
             ) { }

  //default initialization function.
  ngOnInit() {
  }

  /*
   * flag that symbolize whether the form has been submitted
   */
  submitted : boolean = false

  /*
  function for enforcing the validation of the fields in the form.
  */
  async onSubmit({value,valid}){
    if(valid){
      console.log('this is my valuessssss');
      console.log(value);
      let newUser = {
        name: value.name + ' ' + value.lastname,
        occupation: value.currentjob,
        birthDate: value.mydate.formatted,
        martialStatus: value.maritalState
      };
      console.log('user is:::::');
      console.log(newUser);

      // here we will save the in the db the data on the subject, and get his id from db
      let user = await this.subjectService.saveTestSubject(newUser);

      this.complete.emit(user);
    }
    else{
      console.log('Not valid');
      this.submitted = true;
    }
  }
}
