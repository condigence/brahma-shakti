import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService } from '../service/auth.service';
import { AlertService } from '../service/alert.service';
import Swal from 'sweetalert2';
import { UserService } from '../service/user.service';
import { User } from '../model/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  submitted = false;
  returnUrl: string;
  userExist: false;
  error: string;
  user: User;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService
  ) {
    // redirect to home if already logged in
    // if (this.authenticationService.currentUserValue) {
    //   this.router.navigate(['/']);
    // }
  }

  ngOnInit() {
    this.loginForm = this.formBuilder.group(
      {
        contact: [
          '',
          [
            Validators.required,
            Validators.minLength(10),
            Validators.maxLength(10),
          ],
        ],
      }

    );
  
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {

    let userInput = this.loginForm.value.contact;
    console.log(userInput);

    var user = new User();
    user.contact = userInput;
    localStorage.setItem('contact', user.contact);
    console.log(user);
    this.userService.generateOTP(user).subscribe(
      (data) => {
        this.user = data;
        console.log(this.user);
        this.router.navigate(["/otp"], {
          queryParams: { contact: userInput },
        });
      }
    );
  }
}
