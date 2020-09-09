import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
    templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

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
        if (this.authService.isAuthenticated()) {
            this.isLoggedIn = true;
            const user = this.authService.getUser();
            if(user) {
                this.roles = user.roles || [];
            }
        }
    }

    login() {
        this.authService.login(this.credentials)
            .subscribe(
                data => {
                    this.isLoginFailed = false;
                    this.isLoggedIn = true;
                    this.roles = data.roles || [];
                    this.router.navigateByUrl('/');
                    // this.reloadPage();
                },
                err => {
                    this.errorMessage = err.error.message;
                    this.isLoginFailed = true;
                    this.error = true;
                }
            );
    }

    reloadPage(): void {
        window.location.reload();
    }

}
