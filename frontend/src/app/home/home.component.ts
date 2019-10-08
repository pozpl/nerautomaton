import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from "../auth/auth.service";
import {Observable} from "rxjs";
import {UserDto} from "../auth/user.dto.";

@Component({
    templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

    title = 'Demo';
    user$: Observable<UserDto> = null;

    constructor(private app: AuthService,
                private http: HttpClient) {
    }

    authenticated() {
        return this.app.authenticated;
    }

    ngOnInit(): void {
        this.user$ = this.app.isLoggedIn();
    }

}
