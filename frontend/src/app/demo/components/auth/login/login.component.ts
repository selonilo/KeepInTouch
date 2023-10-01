import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/demo/service/auth.service';
import { SweetAlertService } from 'src/app/demo/service/sweetAlertService';
import { LayoutService } from 'src/app/layout/service/app.layout.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styles: [`
        :host ::ng-deep .pi-eye,
        :host ::ng-deep .pi-eye-slash {
            transform:scale(1.6);
            margin-right: 1rem;
            color: var(--primary-color) !important;
        }
    `]
})
export class LoginComponent {

  valCheck: string[] = ['remember'];

  password!: string;

  username?: string;

  email?: string;

  constructor(public layoutService: LayoutService,
    private authService: AuthService,
    private router: Router,
    private sweetAlertService: SweetAlertService) { }

  user = {
    username: this.username,
    email: this.email,
    password: this.password
  }

  login() {
    this.user.username = this.username;
    this.user.email = this.email;
    this.user.password = this.password;
    this.authService.login(this.user).subscribe(response => {
      localStorage.setItem('token',response.token)
      this.sweetAlertService.showSuccessAlert("");
      this.router.navigate(['/']);
    },
      error => {
        this.sweetAlertService.showErrorAlert(error.message);
      })
  }
}
