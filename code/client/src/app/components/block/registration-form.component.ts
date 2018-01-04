import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-registration-form',
  templateUrl: './registration-form.component.html',
  styleUrls: ['./registration-form.component.css']
})
export class registrationFormComponent implements OnInit {
  user={
    name:'',
    lastname:'',
    id:'',
    birthdate:'',
    maritalstatus:'',
    currentjob:''
  }
  constructor() { }

  ngOnInit() {
  }

  
  onSubmit({value,valid}){
    if(valid){
      console.log(value);
    }
    else{
      console.log('Not valid');
    }
  }
}