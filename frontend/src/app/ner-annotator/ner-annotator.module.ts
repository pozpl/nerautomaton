import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TextItemComponent } from './text-item/text-item.component';
import {MaterialModule} from "../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {TextSelectDirective} from "./text-item/text-select.directive";

@NgModule({
  declarations: [
    TextItemComponent,
    TextSelectDirective
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule
  ]
})
export class NerAnnotatorModule { }
