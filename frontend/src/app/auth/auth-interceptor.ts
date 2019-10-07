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

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(private auth: AuthService,
                private router: Router) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        return next.handle(request).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
                // do stuff with response if needed
            }
        }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                if (err.status === 401) {
                    // redirect to the login route
                    // or show a modal
                    this.auth.authenticated = false;
                    this.router.navigate(['/login']);
                }
            }
        });
    }
}
