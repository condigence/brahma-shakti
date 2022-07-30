import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ItemService } from '../../service/item.service';
import { BrandService } from '../../service/brand.service';
import Swal from 'sweetalert2';
import { BSProductService } from 'src/app/service/bs-product.service';
@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {
  brands: any;
  addForm: FormGroup;
  submitted = false;
  imageId: number;
  messageToSendP = 'ITEM';
  mandatoryFields = '*Mandatory fields';

  constructor(
    private router: Router,
    private formBuilder: FormBuilder,   
    private bsProductService: BSProductService
  ) {}

  category = [
    { id: 1, categoryName: 'Sweets' },
    { id: 2, categoryName: 'Vegetables' },
    { id: 3, categoryName: 'Dairy Product' },
   
  ];

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      description: ['', Validators.required],
      category: ['', Validators.required],
      title: ['', Validators.required],
      price: ['', Validators.required],
      unit: ['', Validators.required],
      discount: ['', Validators.required],    
      rating: ['', Validators.required],
      stockLeft: ['', Validators.required],
      image: [''],
      promoCodes: ['', Validators.required],
      subscribe: ['', Validators.required],
    });
    // this.brandService.getAllBrands().subscribe((data) => {
    //   this.brands = data;
    // });
  }

  receiveMessage($event) {
    this.imageId = $event;
    this.addForm.controls.imageId.setValue(this.imageId);
  }
  /* Select Dropdown error handling */
  public handleError = (controlName: string, errorName: string) => {
    return this.addForm.controls[controlName].hasError(errorName);
  }

  // changeCapacity(e) {
  //   this.addForm.get("capacity").setValue(e.target.value, {
  //     onlySelf: true,
  //   });
  // }

  // changeBrand(e) {
  //   this.addForm.get("name").setValue(e.target.value, {
  //     onlySelf: true,
  //   });
  // }

  get f() {
    return this.addForm.controls;
  }

  onSubmit() {
    console.log(this.addForm.value);
    
    this.submitted = true;
    if (this.addForm.valid) {
      this.bsProductService.addProduct(this.addForm.value).subscribe((data) => {
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Item added',
          text: 'Successfully',
          showConfirmButton: true,
          timer: 3000,
          timerProgressBar: true,
        });
        this.router.navigate(['item/list-item']);
      });
    } else {
      console.log('form not valid!');
    }
  }
}
