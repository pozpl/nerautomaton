import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {TextItemComponent} from './text-item/text-item.component';
import {MaterialModule} from "../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {TextSelectDirective} from "./text-item/text-select.directive";
import {TermsAnnotationsService} from "./text-item/terms-annotations.service";
import {ResultsReviewComponent} from './results-review/results-review.component';
import {NerJobsComponent} from './ner-jobs/ner-jobs.component';
import {NerJobsService} from "./ner-jobs/ner-jobs.service";
import {NerJobEditComponent} from './ner-jobs/ner-job-edit/ner-job-edit.component';
import {AppRoutingModule} from "../app-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { NerJobTextsListComponent } from './ner-job-texts-list/ner-job-texts-list.component';
import {NerJobTextAddComponent} from './ner-job-text-edit/ner-job-text-add.component';
import {NerJobTextEditDialog} from "./ner-job-text-edit/ner-job-text-edit-dialog.component";

@NgModule({
    declarations: [
        TextItemComponent,
        TextSelectDirective,
        ResultsReviewComponent,
        NerJobsComponent,
        NerJobEditComponent,
        NerJobTextsListComponent,
        NerJobTextAddComponent,
        NerJobTextEditDialog
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
