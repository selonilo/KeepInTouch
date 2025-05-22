import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PROJECT_CONSTANTS } from '../constant/project.constants';
import { PasswordRefreshModel } from '../auth/model/password-refresh-model';
import { ResponseMessageModel } from '../common/model/response-message.model';
import { LoginModel } from '../auth/model/login.model';
import { TokenModel } from '../auth/model/token.model';
import { UserModel } from '../auth/model/user.model';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = PROJECT_CONSTANTS.API_URL + 'auth';

    constructor(private http: HttpClient) {}

    refreshPassword(passwordRefreshModel: PasswordRefreshModel): Observable<ResponseMessageModel> {
        return this.http.post<ResponseMessageModel>(this.apiUrl + '/refreshPassword', passwordRefreshModel);
    }

    login(loginModel: LoginModel): Observable<TokenModel> {
        return this.http.post<TokenModel>(this.apiUrl + '/login', loginModel);
    }

    update(userModel: UserModel): Observable<UserModel> {
        return this.http.post<UserModel>(this.apiUrl + '/update', userModel);
    }

    register(userModel: UserModel): Observable<UserModel> {
        return this.http.post<UserModel>(this.apiUrl + '/register', userModel);
    }
}
