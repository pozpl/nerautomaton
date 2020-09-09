import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, ReplaySubject} from "rxjs";
import {JwtResponseDto} from "./jwt-response.dto";
import {TokenStorageService} from "./token-storage.service";
import {tap} from "rxjs/operators";
import {UserDto} from "./user.dto.";


@Injectable()
export class AuthService {

    public static readonly SIGN_IN_URL = "/api/auth/signin";
    public static readonly SIGN_UP_URL = "/api/auth/signup";

    private userSubject = new ReplaySubject<UserDto | null>(1);

    constructor(private http: HttpClient,
                private tokenService: TokenStorageService) {

        const user = tokenService.getUser();
        if(user){
            this.userSubject.next(user);
        }
    }



    login(credentials): Observable<JwtResponseDto> {
        return this.http.post<JwtResponseDto>(AuthService.SIGN_IN_URL , {
            username: credentials.username,
            password: credentials.password
        }).pipe(
            tap(data => {
                this.tokenService.saveToken(data.token);
                const user = new UserDto();
                user.username = data.username;
                user.email = data.email;
                user.roles = data.roles;
                this.tokenService.saveUser(user);
                this.userSubject.next(user);
            })
        );
    }

    register(user): Observable<any> {
        return this.http.post(AuthService.SIGN_UP_URL, {
            username: user.username,
            email: user.email,
            password: user.password
        });
    }


    isAuthenticated(): boolean {
        return this.tokenService.getUser() != null && this.tokenService.getToken() != null;
    }

    signOut() {
        this.tokenService.signOut();
        this.userSubject.next(null);
    }

    getUser(): Observable<UserDto | null>{
        return this.userSubject.asObservable();
    }

}
