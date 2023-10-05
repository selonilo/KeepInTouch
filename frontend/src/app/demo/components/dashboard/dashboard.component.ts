import { Component, OnInit, OnDestroy } from '@angular/core';
import { PostService } from '../../service/post.service';
import { Post } from '../../api/post';
import { SweetAlertService } from '../../service/sweetAlertService';
import { ConfirmationService } from 'primeng/api';

@Component({
    templateUrl: './dashboard.component.html',
    providers: [ConfirmationService]
})
export class DashboardComponent implements OnInit, OnDestroy {

    post?: Post;

    data?: any;

    display?: any;

    value?:any;

    updatePostModal?:any;

    isUpdate: boolean = false;

    constructor(private postService: PostService,
        private sweetAlertService: SweetAlertService) {
    }

    ngOnInit() {
        this.getPosts();
    }

    ngOnDestroy() {
    }

    getPosts() {
        this.postService.getAll().subscribe((response) => {
            this.data = response;
        })
    }

    openPostForm() {
        this.display = true;
    }

    onPostSaved() {
        this.display = false;
        this.isUpdate = false;
        this.getPosts();
    }

    updatePost(post:any) {
        this.isUpdate = true;
        this.value = post.description;
        this.updatePostModal = post;
        this.display = true;
    }

    deletePost(id?: any) {
        this.postService.delete(id).subscribe(() => {
            this.sweetAlertService.showSuccessToast("Başarılı", "Post silindi");
            this.getPosts();
        });
    }
}
