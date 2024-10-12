import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AdminComponent } from './admin.component';
import { PostCategoryComponent } from './components/post-category/post-category.component';
import { PostProductComponent } from './components/post-product/post-product.component';
import { UpdateProductComponent } from './components/update-product/update-product.component';

const routes: Routes = [
  {path: '', component: AdminComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'category', component: PostCategoryComponent},
  {path: 'product', component: PostProductComponent},
  {path: 'product/:productId', component: UpdateProductComponent},


];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
