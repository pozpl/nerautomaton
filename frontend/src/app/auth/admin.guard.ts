import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {AuthService} from "./auth.service";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

    constructor(private authService: AuthService,
                private router: Router) {}


    canActivate(next: ActivatedRouteSnapshot,
                state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        const url: string = state.url;

        return this.checkLogin(url)
    }

    private checkLogin(url: string): Observable<boolean>|UrlTree {
        if (this.authService.isAuthenticated() ){
            return this.authService.getUser().pipe(
                map(userDto => {
                    return userDto !== null
                        && userDto.roles !== undefined
                        && userDto.roles.includes('ROLE_ADMIN')
                })
            );
        }

        // Store the attempted URL for redirecting
        //this.authService.redirectUrl = url;

        // Redirect to the login page
        return this.router.parseUrl('/login');
    }

}
