import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  user={
    name:'',
    password:'',
    rememberMe:false
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
