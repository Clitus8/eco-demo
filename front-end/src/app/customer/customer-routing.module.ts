import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CustomerComponent } from './customer.component';
import { ViewProductDetailComponent } from './components/view-product-detail/view-product-detail.component';

const routes: Routes = [
  {path: '', component: CustomerComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'product/:productId', component: ViewProductDetailComponent},

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomerRoutingModule { }
