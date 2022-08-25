import { Component, OnInit } from "@angular/core";
import { User } from "src/app/model/user.model";
import { Router } from "@angular/router";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { first } from "rxjs/operators";
import { UserService } from "src/app/service/user.service";
import Swal from "sweetalert2";
import { Profile } from "src/app/model/profile.model";

@Component({
  selector: "app-my-profile",
  templateUrl: "./my-profile.component.html",
  styleUrls: ["./my-profile.component.scss"],
})
export class MyProfileComponent implements OnInit {
  convertedImage: any;
  isUserActive: any;
  user: User;
  profile: any;
  editForm: FormGroup;
  imageId: string;
  messageToSendP = "PROFILE";

  constructor(
    private userService: UserService,
    private router: Router,
    private formBuilder: FormBuilder
  ) {}

  ngOnInit() {
    this.editForm = this.formBuilder.group({
      id: [],
      name: ["", Validators.required],
      imageId: [""],
      email: ["", Validators.required],
      contact: ["", Validators.required],
    });

    this.getProfileView();
  }

  getProfileView(): void {
    var userId;
    const currentUser = localStorage.getItem("currentUser");
    console.log(currentUser);
    this.userService.getUserByContact(JSON.parse(currentUser).contact)
      .subscribe((data) => {
        this.user = data;
        console.log(this.user);
        userId = this.user.id;
        console.log(userId);
      });

     
     
      

  }

  receiveMessage($event) {
    this.imageId = $event;
    this.editForm.controls.imageId.setValue(this.imageId);
  }

  get f() {
    return this.editForm.controls;
  }

  onSubmit() {
    
    console.log(this.editForm.valid);
    this.editForm.controls.id.setValue(this.user.id);
    this.editForm.controls.contact.setValue(this.user.contact);
    console.log(this.editForm.value);
    this.userService
      .updateUser(this.editForm.value)
      .pipe(first())
      .subscribe(
        (data) => {
          Swal.fire({
            position: "top-end",
            icon: "success",
            title: "You updated your Profile",
            showConfirmButton: true,
            timer: 3000,
          });
          // this.router.navigate(['/profile/my-profile']);
          //this.profile = data.
          console.log(data);
          this.router.navigate(["/"]);
        },
        (error) => {
          alert(error);
        }
      );
  }
}
