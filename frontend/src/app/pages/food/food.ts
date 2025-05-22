import { Component, OnInit, ViewChild } from '@angular/core';
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
import { MaterialService } from '../service/material.service';
import { FoodModel } from './model/food.model';
import { FoodService } from '../service/food.service';
import { CardModule } from 'primeng/card';
import { FileUpload, UploadEvent } from 'primeng/fileupload';
import { PROJECT_CONSTANTS } from '../constant/project.constants';
import { FoodMaterial } from './food-material/food-material';

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
    selector: 'app-material',
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
        CardModule,
        FileUpload,
        FoodMaterial
    ],
    templateUrl: 'food.html',
    providers: [MessageService, MaterialService, ConfirmationService]
})
export class Food implements OnInit {
    formDialog: boolean = false;
    detailDialog: boolean = false;

    submitted: boolean = false;

    @ViewChild('dt') dt!: Table;

    exportColumns!: ExportColumn[];

    cols!: Column[];

    tableList: FoodModel[] = [];

    selectedItem!: FoodModel;

    loading: boolean = false;

    filePath: string = PROJECT_CONSTANTS.FILE_PATH;

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private service: FoodService,
        private materialService: MaterialService
    ) {}

    exportCSV() {
        this.dt.exportCSV();
    }

    ngOnInit() {
        this.getList();
    }

    getList() {
        this.loading = true;
        this.service.getList().subscribe({
            next: (data) => {
                this.tableList = data;
                this.loading = false;
            },
            error: (err) => {
                console.log(err);
            }
        });
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    openNew() {
        this.selectedItem = <FoodModel>{};
        this.submitted = false;
        this.formDialog = true;
    }

    edit(selectedItem: FoodModel) {
        this.selectedItem = { ...selectedItem };
        this.formDialog = true;
    }

    openDetail(selectedItem: FoodModel) {
        this.selectedItem = { ...selectedItem };
        this.detailDialog = true;
    }

    hideDialog() {
        this.formDialog = false;
        this.submitted = false;
    }

    hideDetailDialog() {
        this.detailDialog = false;
    }

    delete(selectedItem: FoodModel) {
        this.confirmationService.confirm({
            message: selectedItem.name + ' silmek istediğinize emin misiniz' + '?',
            header: 'Onaylama',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.service.deleteById(selectedItem.id).subscribe({
                    next: (data) => {
                        this.selectedItem = <FoodModel>{};
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Başarılı',
                            detail: 'Seçili ürün silindi',
                            life: 3000
                        });
                        this.getList();
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
        if (this.selectedItem.name?.trim()) {
            if (this.selectedItem.id) {
                this.service.save(this.selectedItem).subscribe({
                    next: (data) => {
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Başarılı',
                            detail: 'Seçili ürün güncellendi',
                            life: 3000
                        });
                        this.getList();
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
                        this.getList();
                    },
                    error: (err) => {
                        console.log(err);
                    }
                });
            }

            this.formDialog = false;
            this.selectedItem = <FoodModel>{};
        }
    }

    onUpload(event: any) {
        const file: File = event.files[0];
        if (file) {
            this.service.uploadImage(this.selectedItem.id, file).subscribe({
                next: (response) => {
                    this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Resim Yüklendi.' });
                    this.selectedItem.imageUrl = response.imageUrl;
                    this.tableList.forEach((item) => {
                        if (item.id === this.selectedItem.id) {
                            item.imageUrl = response.imageUrl;
                        }
                    });
                },
                error: () => {
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Resim Yüklenirken Hata Oluştu.' });
                }
            });
        }
    }

    onDeleteImage() {
        this.service.deleteImage(this.selectedItem.id).subscribe({
            next: () => {
                this.messageService.add({ severity: 'info', summary: 'Success', detail: 'Resim Silindi.' });
                this.selectedItem.imageUrl = '';
                this.tableList.forEach((item) => {
                    if (item.id === this.selectedItem.id) {
                        item.imageUrl = '';
                    }
                });
            },
            error: () => {
                this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Resim Silinirken Hata Oluştu.' });
            }
        });
    }
}
