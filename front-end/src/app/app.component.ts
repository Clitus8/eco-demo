import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { UserStorageService } from './services/storage/user-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'client';

isCustomerLoggedIn: boolean = UserStorageService.isCustomerLoggedIn();
isAdminLoggedIn: boolean = UserStorageService.isAdminLoggedIn();

  constructor(private modalService: NgbModal,
    private router: Router
  ) {
  }

  public open(modal: any): void {
    this.modalService.open(modal);
  }

  ngOnInit() {
    this.router.events.subscribe(() => {
    this.isCustomerLoggedIn = UserStorageService.isCustomerLoggedIn();
    this.isAdminLoggedIn = UserStorageService.isAdminLoggedIn();
})
  }

  logout() {
    UserStorageService.signOut();
    this.router.navigateByUrl('/login');
  }
  navigateToDashboard() {
    if (this.isAdminLoggedIn) {
      this.router.navigate(['/admin/dashboard']);
    } else if (this.isCustomerLoggedIn) {
      this.router.navigate(['/customer/dashboard']);
    }
  }
}
