import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from "../auth/auth.service";
import {UserDto} from "../auth/user.dto.";

@Component({
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    user: UserDto = null;

    constructor(private app: AuthService,
                private http: HttpClient) {
    }

    authenticated() {
        return this.app.authenticated;
    }

    ngOnInit(): void {
        this.app.isLoggedIn()
            .subscribe(user => {
                this.user = user;
            });
    }

}
