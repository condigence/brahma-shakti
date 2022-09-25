import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService } from '../service/auth.service';
import { AlertService } from '../service/alert.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-otp',
  templateUrl: './otp.component.html',
  styleUrls: ['./otp.component.scss']
})
export class OTPComponent implements OnInit {
  otpForm: FormGroup;
  submitted = false;
  returnUrl: string;
  userExist = false;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService
  ) {
    // redirect to home if already logged in
    if (this.authenticationService.currentUserValue) {
      this.router.navigate(['/']);
    }
  }




  ngOnInit() {
    this.otpForm = this.formBuilder.group({
      otp: ['', Validators.required],
      contact: ['']
    });

    // get return url from route parameters or default to '/'
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';
    

    if (this.route.snapshot.queryParamMap.get('registered') == 'true') {
      this.userExist = true;
    }else{
      this.userExist = false;
    }

  }

  // convenience getter for easy access to form fields
  get f() { return this.otpForm.controls; }

  onSubmit() {
    this.submitted = true;
    if (this.otpForm.invalid) {
      return;
    }
    const contact = localStorage.getItem('userContact');
    this.otpForm.controls.contact.setValue(contact);
    console.log("inside validate otp");
   // if (this.route.snapshot.queryParamMap.get('registered') == 'true') {
      
      this.authenticationService.validateOTP(this.otpForm.value)
        .pipe(first())
        .subscribe(
          data => {
            
            Swal.fire({
              icon: 'success',
              width: 600,
              padding: '3em',
              title: 'Your are Logged in!',
              text: 'Welcome to BrahmaShakti!',
              timer: 5000,
              timerProgressBar: true,
              footer: `<strong style="color:purple;">Be Yourself Be Pure!</strong>`,
              backdrop: `
                  rgba(0,0,123,0.4)
                  no-repeat
                `
            });
            this.router.navigate(['']);
          },
          error => {
            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              width: 500,
              padding: '1em',
              text: `You entered a wrong OTP!`,
              footer: `<strong style="color:red;">Please try with a Valid OTP!</strong>`
            });
            this.alertService.error(error);
            this.error = error;
          });

  //  } else {

      // Swal.fire({
      //   icon: 'success',
      //   width: 600,
      //   padding: '3em',
      //   title: 'Your are Logged in!',
      //   text: 'Welcome to BrahmaShakti!',
      //   timer: 5000,
      //   timerProgressBar: true,
      //   footer: `<strong style="color:purple;">Please Login again and complete your Profile!</strong>`,
      //   backdrop: `
      //     rgba(0,0,123,0.4)
      //     left top
      //     no-repeat
      //   `
      // });
      // this.router.navigate(['profile/my-profile']);

     
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          width: 500,
          padding: '1em',
          text: `You entered a wrong OTP!`,
          footer: `<strong style="color:red;">Please try with a Valid OTP!</strong>`
        });
       
  

   // }
  }
}
