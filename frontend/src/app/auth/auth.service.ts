import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MatDialog} from "@angular/material/dialog";
import {ErrorDialogComponent} from "../shared-components/error-dialog/error-dialog.component";
import {UserDto} from "./user.dto.";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";


@Injectable()
export class AuthService {

    private _authenticated = false;
    private _user: UserDto = null;

    constructor(private http: HttpClient,
                private  dialog: MatDialog) {
    }

    get authenticated(): boolean {
        return this._authenticated;
    }


    set authenticated(value: boolean) {
        this._authenticated = value;
    }

    authenticate(credentials, callback) {

        const headers = new HttpHeaders(credentials ? {
            authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        } : {});

        this.http.get('/login', {headers: headers}).subscribe(response => {
                if (response['name']) {
                    this._authenticated = true;
                } else {
                    this._authenticated = false;
                }
                return callback && callback();
            },
            error => {
                this.dialog.open(ErrorDialogComponent, {
                    data: {
                        message: "Your login information are incorrect!"
                    }
                });
            } // error path
        );

    }

    isLoggedIn(): Observable<UserDto> {

        return this.http.get<UserDto>('/user', {})
            .pipe(
                map(response => {
                    if (response.name) {
                        this._authenticated = true;
                    } else {
                        this._authenticated = false;
                    }

                    this._user = response;

                    return response;
                })
            );
    }

}
