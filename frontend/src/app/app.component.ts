import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserDto} from "./auth/user.dto.";
import {AuthService} from "./auth/auth.service";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

    private user: UserDto | null = null;

    constructor(public authService: AuthService,
                private http: HttpClient,
                private router: Router) {
    }


    ngOnInit(): void {
        this.user = this.authService.getUser();
    }

    logout() {
        this.authService.signOut();
        this.router.navigate(['/login']);
    }

}
