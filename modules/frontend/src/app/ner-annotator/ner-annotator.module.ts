import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';


import {MaterialModule} from "../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppRoutingModule} from "../app-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TextItemComponent} from "./annotation/text-item/text-item.component";
import {TextSelectDirective} from "./annotation/text-item/text-select.directive";
import {NerJobTextEditDialog} from "./management/ner-job-text-edit/ner-job-text-edit-dialog.component";
import {NerJobEditComponent} from "./management/ner-jobs/ner-job-edit/ner-job-edit.component";
import {NerJobsComponent} from "./management/ner-jobs/ner-jobs.component";
import {NerJobTextAddComponent} from "./management/ner-job-text-edit/ner-job-text-add.component";
import {NerJobTextsListComponent} from "./management/ner-job-texts-list/ner-job-texts-list.component";
import {TermsAnnotationsService} from "./annotation/text-item/terms-annotations.service";
import {NerJobsService} from "./management/ner-jobs/ner-jobs.service";
import { TasksListComponent } from './annotation/tasks-list/tasks-list.component';
import { ProcessedTextsListComponent } from './annotation/processed-texts-list/processed-texts-list.component';
import { UnprocessedAnnotationPageComponent } from './annotation/unprocessed-annotation-page/unprocessed-annotation-page.component';
import {ResultsReviewComponent} from "./annotation/text-item/results-review/results-review.component";
import { ReviewAnnotationPageComponent } from './annotation/review-annotation-page/review-annotation-page.component';
import { NerJobCsvUploadComponent } from './management/ner-job-csv-upload/ner-job-csv-upload.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { TextCsvUploadDialogComponent } from './management/ner-job-csv-upload/text-csv-upload-dialog/text-csv-upload-dialog.component';
import {TextCsvFileUploadService} from "./management/ner-job-csv-upload/text-csv-file-upload.service";
import { JobOwnResultsDownloadComponent } from './management/results-download/job-own-results-download/job-own-results-download.component';

@NgModule({
    declarations: [
        TextItemComponent,
        TextSelectDirective,
        ResultsReviewComponent,
        NerJobsComponent,
        NerJobEditComponent,
        NerJobTextsListComponent,
        NerJobTextAddComponent,
        NerJobTextEditDialog,
        TasksListComponent,
        ProcessedTextsListComponent,
        UnprocessedAnnotationPageComponent,
        ReviewAnnotationPageComponent,
        NerJobCsvUploadComponent,
        TextCsvUploadDialogComponent,
        JobOwnResultsDownloadComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        FlexLayoutModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
    ],
    providers: [
        TermsAnnotationsService,
        NerJobsService,
        TextCsvFileUploadService
    ]
})
export class NerAnnotatorModule {
}
