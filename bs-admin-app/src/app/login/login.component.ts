import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService } from '../service/auth.service';
import Swal from 'sweetalert2';
import { UserService } from '../service/user.service';

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
  user: any;

  constructor(
    private formBuilder: FormBuilder,    
    private router: Router,
    private authenticationService: AuthenticationService,    
    private userService: UserService
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
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
    // let userInput = this.loginForm.value.contact;
    // this.userService.getUserByContact(userInput).subscribe((user) => (this.user = user));
    // get return url from route parameters or default to '/'
    // this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.loginForm.controls;
  }

  onSubmit() {
   
        this.submitted = true;        
        this.authenticationService
          .verifyLogin(this.loginForm.value)
          .pipe(first())
          .subscribe((data) => {
            this.user = data;
            Swal.fire({
              position: "center",
              icon: "success",
              title: "Your are Redirected to otp page",
              text: "Proceed for OTP!",
              showConfirmButton: true,
              timerProgressBar: true,
              timer: 5000,
              footer: `<strong style="color:purple;">Go for OTP!</strong>`,
            });
            console.log('is User Registered ? '+this.user.registered);
            this.router.navigate(["/otp"], {
              queryParams: { 'registered': this.user.registered },
            });
          }); 
  }
}
