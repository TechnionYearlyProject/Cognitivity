import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth-service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  user={
    email:'',
    password:'',
    rememberMe:false
  }
  correctUser = true;
  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit() {
  }

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
