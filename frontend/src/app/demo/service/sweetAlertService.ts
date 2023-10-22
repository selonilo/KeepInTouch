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

  showSuccessToast(title:any, text:any){
    Swal.fire({
      icon: 'success',
      title: title,
      text: text,
      toast: true, // Yandan çıkan uyarı (toast) olarak göstermek için bu seçeneği kullanın.
      position: 'top-end', // Uyarının nerede görüneceğini belirleyin.
      showConfirmButton: false, // Onay düğmesini gizleyin, sadece bir bildirim olarak göstermek için.
      timer: 3000 // Uyarının ne kadar süreyle görüneceğini belirleyin (ms cinsinden).
    });
  }

  showErrorToast(title:any, text:any){
    Swal.fire({
      icon: 'error',
      title: title,
      text: text,
      toast: true, // Yandan çıkan uyarı (toast) olarak göstermek için bu seçeneği kullanın.
      position: 'top-end', // Uyarının nerede görüneceğini belirleyin.
      showConfirmButton: false, // Onay düğmesini gizleyin, sadece bir bildirim olarak göstermek için.
      timer: 3000 // Uyarının ne kadar süreyle görüneceğini belirleyin (ms cinsinden).
    });
  }
  
}