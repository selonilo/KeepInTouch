import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PROJECT_CONSTANTS } from '../constant/project.constants';
import { FoodModel } from '../food/model/food.model';

@Injectable({
    providedIn: 'root'
})
export class FoodService {
    private apiUrl = PROJECT_CONSTANTS.API_URL + 'food';

    constructor(private http: HttpClient) {}

    save(food: FoodModel): Observable<FoodModel> {
        return this.http.post<FoodModel>(this.apiUrl + '/save', food);
    }

    deleteById(id: number): Observable<void> {
        return this.http.delete<void>(this.apiUrl + '/deleteById/' + id);
    }

    uploadImage(foodId: number, file: File): Observable<{ imageUrl: string }> {
        const formData = new FormData();
        formData.append('file', file);
        return this.http.post<{ imageUrl: string }>(this.apiUrl + '/uploadImage/' + foodId, formData);
    }

    deleteImage(foodId: number): Observable<void> {
        return this.http.delete<void>(this.apiUrl + '/deleteImage/' + foodId);
    }

    getImage(foodId: number): Observable<string> {
        return this.http.get<string>(this.apiUrl + '/getImage/' + foodId);
    }

    getList(): Observable<FoodModel[]> {
        return this.http.get<FoodModel[]>(this.apiUrl + '/getList');
    }
}
