import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserDto} from "./auth/user.dto.";
import {AuthService} from "./auth/auth.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy{

    private unsubscribe = new Subject<void>();

    public user: UserDto | null = null;

    constructor(public authService: AuthService,
                private http: HttpClient,
                private router: Router) {
    }


    ngOnInit(): void {
        this.authService.getUser()
            .pipe(takeUntil(this.unsubscribe))
            .subscribe(user => {
                this.user = user;
            });
    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

    logout() {
        this.authService.signOut();
        this.router.navigate(['/login']);
    }

}
