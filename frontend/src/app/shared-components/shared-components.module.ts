import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MaterialModule} from "../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppRoutingModule} from "../app-routing.module";
import {FormsModule} from "@angular/forms";
import {ErrorDialogComponent} from "./error-dialog/error-dialog.component";

@NgModule({
    declarations: [
        ErrorDialogComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        FlexLayoutModule,
        AppRoutingModule,
        FormsModule
    ],
    providers: [
    ]
})
export class SharedComponentsModule {
}
