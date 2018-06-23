import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { SubjectService } from '../../services/database-service/index';
import { TestSubject } from '../../models/index';
import {IMyDpOptions} from 'mydatepicker';

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

  options: IMyDpOptions = {
    disableSince: {day: 0, month: 0, year: 0}
  };
  lastname : string;
  @Output() complete: EventEmitter<TestSubject> = new EventEmitter();

  //default constructor.
  constructor(
                private subjectService : SubjectService
             ) { }

  //default initialization function.
  ngOnInit() {
    let d: Date = new Date();
    d.setDate(d.getDate() + 1);
    this.options.disableSince = {day: d.getDate(), month: d.getMonth() + 1, year: d.getFullYear()};
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
      let newUser = {
        name: value.name.trim() + ' ' + value.lastname.trim(),
        occupation: value.currentjob.trim(),
        birthDate: value.mydate.formatted.trim(),
        martialStatus: value.maritalState.trim()
      };

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
