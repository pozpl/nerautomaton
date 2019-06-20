import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";

import { FormsModule } from '@angular/forms';

import { HomeComponent } from './home/home.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {MaterialModule} from './material.module';
import {FlexLayoutModule} from '@angular/flex-layout';


import {NerAnnotatorModule} from "./ner-annotator/ner-annotator.module";
import {SharedComponentsModule} from "./shared-components/shared-components.module";
import {AuthModule} from "./auth/auth.module";


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,

    SharedComponentsModule,
    AuthModule,
    NerAnnotatorModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent],
  entryComponents: [
  ],
})
export class AppModule {
}


