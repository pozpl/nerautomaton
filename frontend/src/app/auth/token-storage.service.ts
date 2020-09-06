import { Injectable } from '@angular/core';
import {UserDto} from "./user.dto.";

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }


    signOut(): void {
        window.sessionStorage.clear();
    }

    public saveToken(token: string): void {
        window.sessionStorage.removeItem(TOKEN_KEY);
        window.sessionStorage.setItem(TOKEN_KEY, token);
    }

    public getToken(): string | null {
        return sessionStorage.getItem(TOKEN_KEY);
    }

    public saveUser(user: UserDto): void {
        window.sessionStorage.removeItem(USER_KEY);
        window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
    }

    public getUser(): UserDto | null {
        const userDtoStr = sessionStorage.getItem(USER_KEY)
        if(! userDtoStr){
            return null;
        }
        return JSON.parse(userDtoStr);
    }

    public removeToken(): void {
      window.sessionStorage.removeItem(TOKEN_KEY);
    }
    public removeUser(): void {
      window.sessionStorage.removeItem(USER_KEY);
    }

}
