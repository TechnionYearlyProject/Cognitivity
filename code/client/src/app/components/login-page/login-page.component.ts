import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})

/*
the login page component. 
*/
export class LoginPageComponent implements OnInit {

  //define the properties for a user's  credentials , and a remember me option.
  user={
    email:'',
    password:'',
    rememberMe:false
  }

  //Boolean to check if no email was given
  noEmail: boolean = false;

  //Boolean to check if no password was given
  noPassword: boolean = false;
  
  //we assume it's the correct user.
  correctUser = true;
  //Regex for email
  REGEX_EMAIL_FORMAT_INPUT = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

  //default constructor.
  constructor(private router: Router, private authService: AuthService) { }

  //default initialization function.
  ngOnInit() {
  }

  /*
  Input - tuple of value , and is valid indicator.
  Output - (triggered on button click), sends the credentials to the auth server.
  */
  onSubmit({value,valid}){
    if(valid){
      this.authService.login(value.email, value.password)
      .then(res => {
        this.router.navigate(['/dashboard']);
      })
      .catch((err) => {
        if (err.code.startsWith('auth/')) {
          this.correctUser = false;
        }
      })
    }
    else{
      if (this.user.email == '') {
        this.noEmail = true;
      }

      if (this.user.password == '') {
        this.noPassword = true;
      }
    }
  }
}
