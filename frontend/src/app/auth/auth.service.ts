import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, ReplaySubject} from "rxjs";
import {TokenStorageService} from "./token-storage.service";
import {tap} from "rxjs/operators";
import {UserDto} from "./user.dto.";
import {AuthResultDto} from "./auth-result.dto";


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



    public login(credentials): Observable<AuthResultDto> {
        return this.http.post<AuthResultDto>(AuthService.SIGN_IN_URL , {
            username: credentials.username,
            password: credentials.password
        }).pipe(
            tap(authResponse => {
                if(authResponse.jwtResponse) {
                    const jwtResponse = authResponse.jwtResponse;
                    this.tokenService.saveToken(jwtResponse.token);
                    const user = new UserDto();
                    user.username = jwtResponse.username;
                    user.email = jwtResponse.email;
                    user.roles = jwtResponse.roles;
                    this.tokenService.saveUser(user);
                    this.userSubject.next(user);
                }
            })
        );
    }

    public register(user): Observable<any> {
        return this.http.post(AuthService.SIGN_UP_URL, {
            username: user.username,
            email: user.email,
            password: user.password
        });
    }


    public isAuthenticated(): boolean {
        return this.tokenService.getUser() != null && this.tokenService.getToken() != null;
    }

    public signOut() {
        this.tokenService.signOut();
        this.userSubject.next(null);
    }

    public getUser(): Observable<UserDto | null>{
        return this.userSubject.asObservable();
    }

}
