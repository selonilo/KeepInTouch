import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MaterialModel } from '../material/model/material.model';
import { PROJECT_CONSTANTS } from '../constant/project.constants';

@Injectable({
    providedIn: 'root'
})
export class MaterialService {
    private apiUrl = PROJECT_CONSTANTS.API_URL + 'material';

    constructor(private http: HttpClient) {}

    save(material: MaterialModel): Observable<MaterialModel> {
        return this.http.post<MaterialModel>(this.apiUrl + '/save', material);
    }

    deleteById(id: number): Observable<void> {
        return this.http.delete<void>(this.apiUrl + '/deleteById/' + id);
    }

    getMaterialList(): Observable<MaterialModel[]> {
        return this.http.get<MaterialModel[]>(this.apiUrl + '/getList');
    }
}
