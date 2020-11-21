import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {Subject} from "rxjs";

@Component({
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit,OnDestroy {

    private unsubscribe = new Subject<void>();

    credentials = {username: '', password: ''};
    error = false;

    isLoggedIn = false;
    isLoginFailed = false;
    errorMessage = '';
    roles: string[] = [];

    constructor(private authService: AuthService,
                private router: Router) {
    }


    ngOnInit(): void {
    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

    login() {
        this.authService.login(this.credentials)
            .subscribe(
                authResponse => {
                    if(authResponse.jwtResponse) {
                        this.isLoginFailed = false;
                        this.isLoggedIn = true;
                        this.roles = authResponse.jwtResponse.roles || [];
                        this.router.navigateByUrl('/');
                        // this.reloadPage();
                    }else{
                        this.isLoginFailed = true;
                        this.error = true;
                    }
                },
                err => {
                    if(err && err.error) {
                        this.errorMessage = err.error.message;
                    }
                    this.isLoginFailed = true;
                    this.error = true;
                }
            );
    }

    reloadPage(): void {
        window.location.reload();
    }

}
