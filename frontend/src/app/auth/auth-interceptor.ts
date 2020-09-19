import {Injectable} from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor, HttpResponse, HttpErrorResponse
} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {AuthService} from "./auth.service";

import 'rxjs/add/operator/do';
import {Router} from "@angular/router";
import {TokenStorageService} from "./token-storage.service";


const TOKEN_HEADER_KEY = 'Authorization';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private auth: AuthService,
                private router: Router,
                private token: TokenStorageService
                ) {
    }


    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {


        let authReq = request;
        const token = this.token.getToken();
        if(token != null) {
            authReq = request.clone({headers: request.headers.set(TOKEN_HEADER_KEY, 'Bearer ' + token)})
        }
        return next.handle(authReq).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
                // do stuff with response if needed
            }
        }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                if (err.status === 401) {
                    this.auth.signOut(); //Automatically sign out user if we received unauthenticated response.
                    // redirect to the login route
                    // or show a modal
                    this.router.navigate(['/login']);
                }
            }
        });
    }
}
