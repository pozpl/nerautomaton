import {Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {forkJoin, Observable} from "rxjs";
import {TextCsvFileUploadService} from "../text-csv-file-upload.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {NerJobTextDto} from "../../ner-job-texts-list/ner-job-text.dto";

@Component({
  selector: 'app-text-csv-upload-dialog',
  templateUrl: './text-csv-upload-dialog.component.html',
  styleUrls: ['./text-csv-upload-dialog.component.scss']
})
export class TextCsvUploadDialogComponent implements OnInit {

    progress: { [key: string]: { progress: Observable<number> , error: Observable<boolean>} };
    canBeClosed = true
    primaryButtonText = 'Upload'
    showCancelButton = true
    uploading = false
    uploadSuccessful = false

    @ViewChild('file', {static: false}) file
    public files: Set<File> = new Set()

    constructor(private uploadService: TextCsvFileUploadService,
                public dialogRef: MatDialogRef<TextCsvUploadDialogComponent>,
                @Inject(MAT_DIALOG_DATA) private jobId: number) {
    }

    ngOnInit() {
    }

    addFiles() {
        this.file.nativeElement.click();
    }

    onFilesAdded() {
        const files: { [key: string]: File } = this.file.nativeElement.files;
        for (let key in files) {
            if (!isNaN(parseInt(key))) {
                this.files.add(files[key]);
            }
        }
    }


    closeDialog() {
        // if everything was uploaded already, just close the dialog
        if (this.uploadSuccessful) {
            return this.dialogRef.close(this.uploadSuccessful);
        }

        // set the component state to "uploading"
        this.uploading = true;

        // start the upload and save the progress map
        this.progress = this.uploadService.upload(this.files, this.jobId);

        // convert the progress map into an array
        let allProgressObservables: Observable<number>[] = [];
        for (let key in this.progress) {
            allProgressObservables.push(this.progress[key].progress);
        }

        // Adjust the state variables

        // The OK-button should have the text "Finish" now
        this.primaryButtonText = 'Finish';

        // The dialog should not be closed while uploading
        this.canBeClosed = false;
        this.dialogRef.disableClose = true;

        // Hide the cancel-button
        this.showCancelButton = false;

        // When all progress-observables are completed...
        forkJoin(allProgressObservables).subscribe(end => {
            // ... the dialog can be closed again...
            this.canBeClosed = true;
            this.dialogRef.disableClose = false;

            // ... the upload was successful...
            this.uploadSuccessful = true;

            // ... and the component is no longer uploading
            this.uploading = false;
        });
    }

}
