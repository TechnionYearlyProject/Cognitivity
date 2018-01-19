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
  
  //we assume it's the correct user.
  correctUser = true;

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
      console.log('Not valid');
    }
  }
}
