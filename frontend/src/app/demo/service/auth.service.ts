import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class AuthService {

    private apiUrl = 'http://localhost:8080/api/auth';
    private registerUrl = '/register';
    private loginUrl = '/login';
    private logOutUrl = '/logout';

    constructor(private http: HttpClient) { }

    register(user: any): Observable<any> {
        return this.http.post<any>(this.apiUrl + this.registerUrl ,user);
    }

    login(user: any): Observable<any> {
        return this.http.post<any>(this.apiUrl + this.loginUrl ,user);
    }

    logOut(user: any): Observable<any> {
        return this.http.post<any>(this.apiUrl + this.logOutUrl ,user);
    }
}
