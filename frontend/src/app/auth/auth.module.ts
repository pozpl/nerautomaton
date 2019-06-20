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

@NgModule({
    declarations: [
        LoginComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        FlexLayoutModule,
        AppRoutingModule,
        FormsModule,

        SharedComponentsModule
    ],
    providers: [
        AuthService, { provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true },
    ]
})
export class AuthModule {

}
