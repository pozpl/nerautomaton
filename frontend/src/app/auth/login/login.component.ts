import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {TokenStorageService} from "../token-storage.service";
import {UserDto} from "../user.dto.";

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
                private tokenStorage: TokenStorageService,
                private router: Router) {
    }


    ngOnInit(): void {
        if (this.tokenStorage.getToken()) {
            this.isLoggedIn = true;
            const user = this.tokenStorage.getUser();
            if(user) {
                this.roles = user.roles || [];
            }
        }
    }

    login() {
        this.authService.login(this.credentials)
            .subscribe(
                data => {
                    this.tokenStorage.saveToken(data.token);
                    const user = new UserDto();
                    user.username = data.username;
                    user.email = data.email;
                    user.roles = data.roles;
                    this.tokenStorage.saveUser(user);

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
