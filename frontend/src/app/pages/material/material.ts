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
import { MaterialModel } from './model/material.model';
import { EnumMeasurementUnit } from '../enum/enum.measurement.unit';

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
        ConfirmDialogModule
    ],
    templateUrl: 'material.html',
    providers: [MessageService, MaterialService, ConfirmationService]
})
export class Material implements OnInit {
    formDialog: boolean = false;

    submitted: boolean = false;

    measurementUnitList = Object.keys(EnumMeasurementUnit).map((key) => ({
        label: EnumMeasurementUnit[key as keyof typeof EnumMeasurementUnit],
        value: key
    }));

    @ViewChild('dt') dt!: Table;

    exportColumns!: ExportColumn[];

    cols!: Column[];

    tableList: MaterialModel[] = [];

    selectedItem!: MaterialModel;

    loading: boolean = false;

    constructor(
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private service: MaterialService
    ) {}

    exportCSV() {
        this.dt.exportCSV();
    }

    ngOnInit() {
        this.getMaterialList();
    }

    getMaterialList() {
        this.loading = true;
        this.service.getMaterialList().subscribe({
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
        this.selectedItem = <MaterialModel>{};
        this.submitted = false;
        this.formDialog = true;
    }

    edit(selectedItem: MaterialModel) {
        this.selectedItem = { ...selectedItem };
        this.formDialog = true;
    }

    hideDialog() {
        this.formDialog = false;
        this.submitted = false;
    }

    delete(selectedItem: MaterialModel) {
        this.confirmationService.confirm({
            message: selectedItem.name + ' silmek istediğinize emin misiniz' + '?',
            header: 'Onaylama',
            icon: 'pi pi-exclamation-triangle',
            accept: () => {
                this.service.deleteById(selectedItem.id).subscribe({
                    next: (data) => {
                        this.selectedItem = <MaterialModel>{};
                        this.messageService.add({
                            severity: 'success',
                            summary: 'Başarılı',
                            detail: 'Seçili ürün silindi',
                            life: 3000
                        });
                        this.getMaterialList();
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
                        this.getMaterialList();
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
                        this.getMaterialList();
                    },
                    error: (err) => {
                        console.log(err);
                    }
                });
            }

            this.formDialog = false;
            this.selectedItem = <MaterialModel>{};
        }
    }
}
