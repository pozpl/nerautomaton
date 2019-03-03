import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TextItemComponent } from './text-item/text-item.component';
import {MaterialModule} from "../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {TextSelectDirective} from "./text-item/text-select.directive";
import {TermsAnnotationsService} from "./text-item/terms-annotations.service";
import {ResultsDataService} from "./text-item/results-data.service";

@NgModule({
  declarations: [
    TextItemComponent,
    TextSelectDirective
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule
  ],
  providers: [
      ResultsDataService,
      TermsAnnotationsService
  ]
})
export class NerAnnotatorModule { }
