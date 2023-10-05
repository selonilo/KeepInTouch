import { Component, OnInit, OnDestroy, Input, EventEmitter, Output } from '@angular/core';
import { Post } from 'src/app/demo/api/post';
import { PostService } from 'src/app/demo/service/post.service';

@Component({
    selector: 'app-post-form',
    templateUrl: './postForm.component.html'
})
export class PostFormComponent implements OnInit, OnDestroy {

    @Input() display: any = false;
    @Input() value: any = false;
    @Input() updatePostModal: any;
    @Input() isUpdate: boolean = false;

    @Output() postSaved: EventEmitter<void> = new EventEmitter<void>();

    post?: Post

    userId?: number;

    constructor(private postService: PostService) {
    }

    ngOnInit() {
        this.userId = Number(localStorage.getItem('userId'));
    }

    ngOnDestroy() {
    }

    savePost() {
        this.post = {
            description: this.value,
            userId: this.userId
        }

        this.postService.save(this.post).subscribe(() => {
            this.postSaved.emit();
            this.value = "";
        })
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
        })
    }

}
