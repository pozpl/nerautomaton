import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MatDialog} from "@angular/material/dialog";
import {Observable} from "rxjs";
import {JwtResponseDto} from "./jwt-response.dto";


@Injectable()
export class AuthService {

    public static readonly SIGN_IN_URL = "/api/auth/signin";
    public static readonly SIGN_UP_URL = "/api/auth/signup";

    // private _authenticated = false;
    // private _user: UserDto = null;

    constructor(private http: HttpClient,
                private  dialog: MatDialog) {
    }


    // get authenticated(): boolean {
    //     return this._authenticated;
    // }
    //
    //
    // set authenticated(value: boolean) {
    //     this._authenticated = value;
    // }

    // authenticate(credentials, callback) {
    //
    //     const headers = new HttpHeaders(credentials ? {
    //         authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    //     } : {});
    //
    //     this.http.get('/login', {headers: headers}).subscribe(response => {
    //             if (response['name']) {
    //                 this._authenticated = true;
    //             } else {
    //                 this._authenticated = false;
    //             }
    //             return callback && callback();
    //         },
    //         error => {
    //             this.dialog.open(ErrorDialogComponent, {
    //                 data: {
    //                     message: "Your login information are incorrect!"
    //                 }
    //             });
    //         } // error path
    //     );
    //
    // }

    // isLoggedIn(): Observable<UserDto> {
    //
    //     return this.http.get<UserDto>('/user', {})
    //         .pipe(
    //             map(response => {
    //                 if (response.name) {
    //                     this._authenticated = true;
    //                 } else {
    //                     this._authenticated = false;
    //                 }
    //
    //                 this._user = response;
    //
    //                 return response;
    //             })
    //         );
    // }

    login(credentials): Observable<JwtResponseDto> {
        return this.http.post<JwtResponseDto>(AuthService.SIGN_IN_URL , {
            username: credentials.username,
            password: credentials.password
        });
    }

    register(user): Observable<any> {
        return this.http.post(AuthService.SIGN_UP_URL, {
            username: user.username,
            email: user.email,
            password: user.password
        });
    }

}
