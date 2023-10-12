import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { LayoutService } from "./service/app.layout.service";
import { AuthService } from '../demo/service/auth.service';
import { SweetAlertService } from '../demo/service/sweetAlertService';
import { Router } from '@angular/router';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent implements OnInit {

    plainMenuItems: MenuItem[] = [];

    @ViewChild('menubutton') menuButton!: ElementRef;

    @ViewChild('topbarmenubutton') topbarMenuButton!: ElementRef;

    @ViewChild('topbarmenu') menu!: ElementRef;

    constructor(public layoutService: LayoutService,
        private authService: AuthService,
        private sweetAlertService: SweetAlertService,
        private router: Router) { }

    ngOnInit(): void {
        this.plainMenuItems = [
            {
                items: [
                    {
                        label: 'Profile',
                        icon: 'pi pi-fw pi-user',
                        command: () => {
                            console.log('Ana Sayfa Tıklandı');
                          }
                    },
                    {
                        label: 'Logout',
                        icon: 'pi pi-fw pi-times',
                        command: () => {
                            this.logOut();
                          }
                    }
                ] 
            }
        ];
    }

    user = {
        userId: 1
    }

    logOut() {
        this.user.userId = Number(localStorage.getItem("userId"));
        this.router.navigate(['/auth/login']);
        localStorage.setItem('token','');
        localStorage.setItem('userId','');
        this.sweetAlertService.showSuccessToast("Başarılı", "Hoşçakalın");
        /*this.authService.logOut(this.user).subscribe(response => {
            debugger;
            this.sweetAlertService.showSuccessToast("Başarılı", "Hoşçakalın");
            
        });*/
    }
}
