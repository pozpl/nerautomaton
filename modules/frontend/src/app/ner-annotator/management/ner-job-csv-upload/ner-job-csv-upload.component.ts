import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {TextCsvUploadDialogComponent} from "./text-csv-upload-dialog/text-csv-upload-dialog.component";

@Component({
    selector: 'ner-job-csv-upload',
    templateUrl: './ner-job-csv-upload.component.html',
    styleUrls: ['./ner-job-csv-upload.component.scss']
})
export class NerJobCsvUploadComponent implements OnInit {

    @Input() jobId: number;
    @Output() textUploaded = new EventEmitter<boolean>()

    constructor(public dialog: MatDialog) { }

    public openUploadDialog() {
        let dialogRef = this.dialog.open(TextCsvUploadDialogComponent, {
            width: '50%',
            height: '50%',
            data: this.jobId
        });

        dialogRef.afterClosed().subscribe(status => {
            this.textUploaded.emit(status)
        });
    }

    ngOnInit(): void {
    }

}
