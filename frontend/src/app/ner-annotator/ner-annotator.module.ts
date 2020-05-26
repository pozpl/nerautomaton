import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';


import {MaterialModule} from "../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppRoutingModule} from "../app-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TextItemComponent} from "./annotation/text-item/text-item.component";
import {TextSelectDirective} from "./annotation/text-item/text-select.directive";
import {ResultsReviewComponent} from "./annotation/results-review/results-review.component";
import {NerJobTextEditDialog} from "./management/ner-job-text-edit/ner-job-text-edit-dialog.component";
import {NerJobEditComponent} from "./management/ner-jobs/ner-job-edit/ner-job-edit.component";
import {NerJobsComponent} from "./management/ner-jobs/ner-jobs.component";
import {NerJobTextAddComponent} from "./management/ner-job-text-edit/ner-job-text-add.component";
import {NerJobTextsListComponent} from "./management/ner-job-texts-list/ner-job-texts-list.component";
import {TermsAnnotationsService} from "./annotation/text-item/terms-annotations.service";
import {NerJobsService} from "./management/ner-jobs/ner-jobs.service";
import { TasksListComponent } from './annotation/tasks-list/tasks-list.component';
import { ProcessedTextsListComponent } from './annotation/processed-texts-list/processed-texts-list.component';

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
        ProcessedTextsListComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        FlexLayoutModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule
    ],
    providers: [
        TermsAnnotationsService,
        NerJobsService
    ]
})
export class NerAnnotatorModule {
}
