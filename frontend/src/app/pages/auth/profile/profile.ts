import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ButtonModule } from 'primeng/button';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { RippleModule } from 'primeng/ripple';
import { AppFloatingConfigurator } from '../../../layout/component/app.floatingconfigurator';
import { UserModel } from '../model/user.model';
import { AuthService } from '../../service/auth.service';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { Post } from "../../post/post";

@Component({
    selector: 'app-profile',
    standalone: true,
    imports: [ButtonModule, CheckboxModule, InputTextModule, PasswordModule, FormsModule, RouterModule, RippleModule, ToastModule, CardModule, Post],
    providers: [MessageService],
    templateUrl: 'profile.html'
})
export class Profile implements OnInit {
    id?: number;

    mail: string = '';

    name: string = '';

    surname: string = '';

    location: string = '';

    password: string = '';

    userModel: UserModel = {
        name: "",
        surname: "",
        mail: "",
        password: "",
        location: ""
    }

    constructor(
            private service: AuthService,
            private router: Router,
            private messageService: MessageService
        ) { }

    ngOnInit(): void {
        this.service.getById(Number(localStorage.getItem('userId'))).subscribe({
            next: (data) => {
                this.id = data.id;
                this.mail = data.mail;
                this.name = data.name;
                this.surname = data.surname;
                this.location = data.location;
            }
        })
    }

    update() {
        this.userModel.id = this.id;
        this.userModel.mail = this.mail;
        this.userModel.password = this.password;
        this.userModel.name = this.name;
        this.userModel.surname = this.surname;
        this.userModel.location = this.location;
        this.service.update(this.userModel).subscribe({
            next: (data) => {
                this.messageService.add({ severity: 'success', summary: 'Başarılı', detail: 'İşlem başarılı', life: 3000 });
            },
            error: (err) => {
                this.messageService.add({ severity: 'error', summary: 'Hata', detail: err?.error?.message });
            }
        });

    }
}
