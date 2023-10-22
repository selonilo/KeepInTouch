import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { Post } from 'src/app/demo/api/post';
import { PostService } from 'src/app/demo/service/post.service';
import { SweetAlertService } from 'src/app/demo/service/sweetAlertService';

@Component({
    selector: 'app-post-form',
    templateUrl: './postForm.component.html'
})
export class PostFormComponent implements OnInit {

    @Input() display: any = false;
    @Input() value: any = false;
    @Input() updatePostModal: any;
    @Input() isUpdate: boolean = false;

    @Output() postSaved: EventEmitter<void> = new EventEmitter<void>();

    post?: Post

    userId?: number;

    constructor(private postService: PostService,
        private sweetAlertService: SweetAlertService) {
    }

    ngOnInit() {
        this.userId = Number(localStorage.getItem('userId'));
    }

    savePost() {
        this.post = {
            description: this.value,
            userId: this.userId
        }

        this.postService.save(this.post).subscribe(
            () => {
              this.postSaved.emit();
              this.value = "";
              this.sweetAlertService.showSuccessToast("Başarılı", "Post kaydedildi");
            },
            (error) => {
                this.sweetAlertService.showErrorToast("Hata", error.error);
                this.postSaved.emit();
            }
          );
    }

    updatePost(){
        this.post = {
            id: this.updatePostModal.id,
            description: this.value,
            userId: this.userId
        }

        this.postService.update(this.post).subscribe(() => {
            this.postSaved.emit();
            this.value = "";
            this.sweetAlertService.showSuccessToast("Başarılı", "Post güncellendi");
        })
    }

}
