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
import { FoodMaterialModel } from './model/food-material.model';
import { FoodMaterialService } from '../../service/food-material.service';
import { MaterialService } from '../../service/material.service';

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
    selector: 'app-food-material',
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
        ConfirmDialogModule
    ],
    templateUrl: 'food-material.html',
    providers: [MessageService, FoodMaterialService, ConfirmationService]
})
export class FoodMaterial implements OnInit {
    @Input() foodId: number | undefined;

    formDialog: boolean = false;

    submitted: boolean = false;

    @ViewChild('dt') dt!: Table;

    exportColumns!: ExportColumn[];

    cols!: Column[];

    tableList: FoodMaterialModel[] = [];

    selectedItem!: FoodMaterialModel;

    loading: boolean = false;

    materialComboList: any[] = [];

    totalAmount: number = 0;

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private service: FoodMaterialService,
        private materialService: MaterialService
    ) {}

    exportCSV() {
        this.dt.exportCSV();
    }

    ngOnInit() {
        this.getList();
        this.getMaterialComboList();
    }

    getMaterialComboList() {
        this.materialService.getMaterialList().subscribe({
            next: (data) => {
                this.materialComboList = data.map((material) => ({
                    label: material.name + ' - ' + material.measurementUnit,
                    value: material.id
                }));
            },
            error: (err) => {
                console.log(err);
            }
        });
    }

    getList() {
        this.loading = true;
        this.service.getList().subscribe({
            next: (data) => {
                this.tableList = data;
                this.loading = false;
                this.tableList.forEach((item) => {
                    this.totalAmount += item.amount * item.price;
                });
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
        this.selectedItem = <FoodMaterialModel>{};
        this.submitted = false;
        this.formDialog = true;
    }

    edit(selectedItem: FoodMaterialModel) {
        this.selectedItem = { ...selectedItem };
        this.formDialog = true;
    }

    hideDialog() {
        this.formDialog = false;
        this.submitted = false;
    }

    delete(selectedItem: FoodMaterialModel) {
        this.confirmationService.confirm({
            message: selectedItem.materialName + ' silmek istediğinize emin misiniz' + '?',
            header: 'Onaylama',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.service.deleteById(selectedItem.id).subscribe({
                    next: (data) => {
                        this.selectedItem = <FoodMaterialModel>{};
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
        if (this.foodId != null) {
            this.selectedItem.foodId = this.foodId;
        }
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
        this.selectedItem = <FoodMaterialModel>{};
    }
}
