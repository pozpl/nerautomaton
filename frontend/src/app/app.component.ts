import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {TokenStorageService} from "./auth/token-storage.service";
import {UserDto} from "./auth/user.dto.";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{

    private user: UserDto | null = null;

    constructor(public tokenService: TokenStorageService,
                private http: HttpClient,
                private router: Router) {
    }


    ngOnInit(): void {
        this.user = this.tokenService.getUser();
    }

    logout() {
        this.tokenService.removeToken();
        this.tokenService.removeUser();
        this.router.navigate(['/login']);
    }

}
