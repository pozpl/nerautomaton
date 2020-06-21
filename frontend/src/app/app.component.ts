import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {finalize} from 'rxjs/operators'
import {AuthService} from "./auth/auth.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {

    constructor(public auth: AuthService,
                private http: HttpClient,
                private router: Router) {
        this.auth.isLoggedIn();
    }

    logout() {
        this.http.post('logout', {})
            .pipe(finalize(() => {
                this.auth.authenticated = false;
                this.router.navigateByUrl('/login');
            })).subscribe();
    }
}
