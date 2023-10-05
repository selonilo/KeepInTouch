import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../api/post';

@Injectable({
    providedIn: 'root',
})
export class PostService {

    private apiUrl = 'http://localhost:8080/api/public/post';
    private getAllUrl = '/getAll';
    private saveUrl = '/save';
    private deleteUrl = '/delete';
    private updateUrl = '/update';

    constructor(private http: HttpClient) { }

    getAll(): Observable<any> {
        return this.http.get<any>(this.apiUrl + this.getAllUrl);
    }

    getByUserId(userUd: any): Observable<any> {
        return this.http.get<any>(this.apiUrl + '/' + userUd);
    }

    save(user: Post): Observable<any> {
        return this.http.post<any>(this.apiUrl + this.saveUrl ,user);
    }

    delete(id: any): Observable<any> {
        return this.http.delete<any>(this.apiUrl + this.deleteUrl + '/' + id);
    }

    update(user: Post): Observable<any> {
        return this.http.post<any>(this.apiUrl + this.updateUrl ,user);
    }
}
