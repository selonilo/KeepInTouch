import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { SelectModule } from 'primeng/select';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputNumberModule } from 'primeng/inputnumber';
import { DialogModule } from 'primeng/dialog';
import { TagModule } from 'primeng/tag';
import { InputIconModule } from 'primeng/inputicon';
import { IconFieldModule } from 'primeng/iconfield';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { PostService } from '../service/post.service';
import { EnumPostType } from '../enum/enum.post.type';
import { PostModel } from './model/post.model';
import { PostQueryModel } from './model/post.query.model';
import { DataViewModule } from 'primeng/dataview';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ChipModule } from 'primeng/chip';
import { PROJECT_CONSTANTS } from '../constant/project.constants';
import { Router } from '@angular/router';

interface Column {
    field: string;
    header: string;
    customExportHeader?: string;
}

interface ExportColumn {
    title: string;
    dataKey: string;
}

@Component({
    selector: 'app-post',
    standalone: true,
    imports: [
        CommonModule,
        TableModule,
        FormsModule,
        ButtonModule,
        RippleModule,
        ToastModule,
        ToolbarModule,
        RatingModule,
        InputTextModule,
        TextareaModule,
        SelectModule,
        RadioButtonModule,
        InputNumberModule,
        DialogModule,
        TagModule,
        InputIconModule,
        IconFieldModule,
        ConfirmDialogModule,
        DataViewModule,
        SelectButtonModule,
        ChipModule
    ],
    templateUrl: 'post.html',
    providers: [MessageService, PostService, ConfirmationService]
})
export class Post implements OnInit {
    @Input() isProfilePage: boolean = false;

    formDialog: boolean = false;

    submitted: boolean = false;

    postTypeList = Object.keys(EnumPostType).map((key) => ({
        label: EnumPostType[key as keyof typeof EnumPostType],
        value: key
    }));

    @ViewChild('dt') dt!: Table;

    exportColumns!: ExportColumn[];

    cols!: Column[];

    tableList: PostModel[] = [];

    selectedItem!: PostModel;

    loading: boolean = false;

    totalRecords: number = 0;

    pageSize: number = 10;

    currentPage: number = 0;

    layout: 'list' | 'grid' = 'list';

    options = ['list', 'grid'];

    userId?: number;

    imageUrl: string = '';

    filePath: string = PROJECT_CONSTANTS.FILE_PATH;

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private service: PostService,
        private router: Router
    ) { }

    exportCSV() {
        this.dt.exportCSV();
    }

    ngOnInit() {
        this.findPostWithPagination();
        this.userId = Number(localStorage.getItem('userId'));
    }

    findPostWithPagination(page: number = 0, size: number = 10) {
        this.loading = true;
        const queryModel: PostQueryModel = {};
        if (this.isProfilePage) {
            this.service.getListByUserId(Number(localStorage.getItem('userId'))).subscribe({
                next: (data) => {
                    this.tableList = data;
                    this.loading = false;
                },
                error: (err) => {
                    console.log(err);
                }
            });
        } else {
            this.service.getList(Number(localStorage.getItem('userId'))).subscribe({
                next: (data) => {
                    this.tableList = data;
                    this.loading = false;
                },
                error: (err) => {
                    console.log(err);
                }
            });
        }

    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew() {
        this.selectedItem = <PostModel>{};
        this.submitted = false;
        this.formDialog = true;
    }

    edit(selectedItem: PostModel) {
        this.selectedItem = { ...selectedItem };
        this.formDialog = true;
    }

    hideDialog() {
        this.formDialog = false;
        this.submitted = false;
    }

    delete(selectedItem: PostModel) {
        this.confirmationService.confirm({
            message: selectedItem.title + ' silmek istediğinize emin misiniz' + '?',
            header: 'Onaylama',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.service.deleteById(selectedItem.id).subscribe({
                    next: (data) => {
                        this.selectedItem = <PostModel>{};
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Başarılı',
                            detail: 'Seçili ürün silindi',
                            life: 3000
                        });
                        this.findPostWithPagination();
                    },
                    error: (err) => {
                        console.log(err);
                    }
                });
            }
        });
    }

    save() {
        this.submitted = true;
        if (this.selectedItem.title?.trim()) {
            this.selectedItem.userId = Number(localStorage.getItem('userId'));
            if (this.selectedItem.id) {
                this.service.save(this.selectedItem).subscribe({
                    next: (data) => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Başarılı',
                            detail: 'Seçili ürün güncellendi',
                            life: 3000
                        });
                        this.findPostWithPagination();
                    },
                    error: (err) => {
                        console.log(err);
                    }
                });
            } else {
                this.service.save(this.selectedItem).subscribe({
                    next: (data) => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Başarılı',
                            detail: 'Kaydedildi',
                            life: 3000
                        });
                        this.findPostWithPagination();
                    },
                    error: (err) => {
                        console.log(err);
                    }
                });
            }

            this.formDialog = false;
            this.selectedItem = <PostModel>{};
        }
    }

    likePost(post: PostModel) {
        this.service.likePost(post.id, Number(localStorage.getItem('userId'))).subscribe({
            next: (data) => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Başarılı',
                    detail: 'Kaydedildi',
                    life: 3000
                });
                this.findPostWithPagination();
            },
            error: (err) => {
                console.log(err);
            }
        });
    }

    unLikePost(post: PostModel) {
        this.service.unLikePost(post.id, Number(localStorage.getItem('userId'))).subscribe({
            next: (data) => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Başarılı',
                    detail: 'Kaydedildi',
                    life: 3000
                });
                this.findPostWithPagination();
            },
            error: (err) => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Message Content' });
            }
        });
    }

    routeProfile(userId: any) {
        this.router.navigate(['/pages/profile', userId]);
    }
}
