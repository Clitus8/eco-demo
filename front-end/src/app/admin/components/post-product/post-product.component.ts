import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AdminService } from '../../service/admin.service';

@Component({
  selector: 'app-post-product',
  templateUrl: './post-product.component.html',
  styleUrls: ['./post-product.component.scss'] // Fix here
})
export class PostProductComponent {
  productForm!: FormGroup;
  listOfCategories: any = [];
  selectedFile!: File | null;
  imagepreview!: any;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private snackbar: MatSnackBar,
    private adminService: AdminService
  ) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
    this.previewImage();
  }

  previewImage() {
    const reader = new FileReader();
    reader.onload = () => {
      this.imagepreview = reader.result;
    }
    if (this.selectedFile) {
      reader.readAsDataURL(this.selectedFile);
    }
  }

  ngOnInit(): void {
    this.productForm = this.fb.group({
      categoryId: [null, [Validators.required]],
      name: [null, [Validators.required]],
      price: [null, [Validators.required]],
      description: [null, [Validators.required]],
    });
    this.getAllCategories();
  }

  getAllCategories() {
    this.adminService.getAllCategories().subscribe(res => {
      this.listOfCategories = res;
    });
  }

  addProduct(): void {
    if (this.productForm.valid && this.selectedFile) {
      const formData: FormData = new FormData();
      formData.append('img', this.selectedFile);
      formData.append('categoryId', this.productForm.get('categoryId')!.value);
      formData.append('name', this.productForm.get('name')!.value);
      formData.append('description', this.productForm.get('description')!.value);
      formData.append('price', this.productForm.get('price')!.value);
      
      this.adminService.addProduct(formData).subscribe(
        (res) => {
          this.snackbar.open('Product added successfully!', 'Close', { duration: 3000 });
          this.router.navigate(['/admin/dashboard']);
        },
        error => {
          console.error('Error occurred while posting product:', error);
          this.snackbar.open('Error occurred while adding product', 'Close', { duration: 3000 });
        }
      );
    } else {
      this.snackbar.open('Please fill all fields and select an image.', 'Close', { duration: 3000 });
    }
  }
}
