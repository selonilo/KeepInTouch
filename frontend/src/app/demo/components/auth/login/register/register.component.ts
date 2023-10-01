import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/demo/service/auth.service';
import { SweetAlertService } from 'src/app/demo/service/sweetAlertService';
import { LayoutService } from 'src/app/layout/service/app.layout.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styles: [`
        :host ::ng-deep .pi-eye,
        :host ::ng-deep .pi-eye-slash {
            transform:scale(1.6);
            margin-right: 1rem;
            color: var(--primary-color) !important;
        }
    `]
})
export class RegisterComponent {

    valCheck: string[] = ['remember'];

    password!: string;

    username?: string;

    email?: string;

    user = {
        username: this.username,
        email: this.email,
        password: this.password
    }

    constructor(public layoutService: LayoutService,
        private authService: AuthService,
        private router: Router,
        private sweetAlertService: SweetAlertService) { }

    register() {
        this.user.username = this.username;
        this.user.email = this.email;
        this.user.password = this.password;
        this.authService.register(this.user).subscribe(response => {
            this.router.navigate(['/auth/login']);
            this.sweetAlertService.showSuccessAlert("Kayıt başarılı");
        },
            error => {
                this.sweetAlertService.showErrorAlert(error.message);
            })
    }
}
