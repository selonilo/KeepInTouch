import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root'
})
export class SweetAlertService {
  showSuccessAlert(successMessage: string){
    Swal.fire({
      icon: 'success',
      title: 'Başarılı!',
      text: successMessage
    })
  }
  showErrorAlert(errorMessage: string) {
    Swal.fire({
      icon: 'error',
      title: 'Hata!',
      text: errorMessage,
    });
  }
}