import { Component, OnInit, Output, EventEmitter } from '@angular/core';


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
  user={
    name:'',
    lastname:'',
    id:'',
    birthdate:'',
    maritalstatus:'',
    currentjob:''
  }

  @Output() complete: EventEmitter<number> = new EventEmitter();

  //default constructor.
  constructor() { }

  //default initialization function.
  ngOnInit() {
  }

  /*
  function for enforcing the validation of the fields in the form.
  */
  onSubmit({value,valid}){
    if(valid){
      console.log(value);

      // here we will save the in the db the data on the subject, and get his id from db
      let id = /* get answer from db... */ 1 /* temp hard-coded value */;
      this.complete.emit(id);
    }
    else{
      console.log('Not valid');
    }
  }
}
