import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AuthService} from "../auth/auth.service";

@Component({
    templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {

    title = 'Demo';
    greeting = null;

    constructor(private app: AuthService,
                private http: HttpClient) {
    }

    authenticated() {
        return this.app.authenticated;
    }

    ngOnInit(): void {
        this.http.get('resource').subscribe(data => this.greeting = data);
    }

}
