import {NgModule} from "@angular/core";
import {CommonModule} from "@angular/common";
import {MaterialModule} from "../material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import {AppRoutingModule} from "../app-routing.module";
import {FormsModule} from "@angular/forms";
import {SharedComponentsModule} from "../shared-components/shared-components.module";
import {AuthService} from "./auth.service";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {LoginComponent} from "./login/login.component";
import {XhrInterceptor} from "./xhr-interceptor.interceptor";
import {AuthInterceptor} from "./auth-interceptor";
import { RegisterComponent } from './register/register.component';
import {RegistrationSuccessfulDialogComponent} from "./register/registration-successful-dialog.component";
import { PasswordStrengthBarComponent } from './password-strength-bar/password-strength-bar.component';
import {BrowserModule} from "@angular/platform-browser";

@NgModule({
    declarations: [
        LoginComponent,
        RegisterComponent,
        RegistrationSuccessfulDialogComponent,
        PasswordStrengthBarComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        FlexLayoutModule,
        AppRoutingModule,
        FormsModule,
        BrowserModule,

        SharedComponentsModule
    ],
    providers: [
        AuthService,
        {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true},
        {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
    ]
})
export class AuthModule {

}
