import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";

import { FormsModule } from '@angular/forms';

import { AuthService } from './auth.service';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { HeroesComponent } from './heroes/heroes.component';
import { HeroDetailComponent } from './hero-detail/hero-detail.component';
import { MessagesComponent } from './messages/messages.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

import {MaterialModule} from './material.module';
import {FlexLayoutModule} from '@angular/flex-layout';


import {NerAnnotatorModule} from "./ner-annotator/ner-annotator.module";
import {DataService} from "./data/data.service";
import { PostDialogComponent } from './post-dialog/post-dialog.component';
import { ErrorDialogComponent } from './error-dialog/error-dialog.component';


@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const xhr = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(xhr);
  }
}


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    HeroesComponent,
    HeroDetailComponent,
    MessagesComponent,
    DashboardComponent,
    PostDialogComponent,
    ErrorDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    
    BrowserAnimationsModule,
    MaterialModule,
    FlexLayoutModule,

    NerAnnotatorModule
  ],
  providers: [
    AuthService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true },
    DataService
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    PostDialogComponent,
    ErrorDialogComponent
  ],
})
export class AppModule {
}


