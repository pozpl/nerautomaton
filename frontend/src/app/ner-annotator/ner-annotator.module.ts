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
import {FormsModule} from "@angular/forms";

@NgModule({
    declarations: [
        TextItemComponent,
        TextSelectDirective,
        ResultsReviewComponent,
        NerJobsComponent,
        NerJobEditComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        FlexLayoutModule,
        AppRoutingModule,
        FormsModule
    ],
    providers: [
        TermsAnnotationsService,
        NerJobsService
    ]
})
export class NerAnnotatorModule {
}
