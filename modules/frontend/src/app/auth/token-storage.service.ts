import {Inject, Injectable} from '@angular/core';
import {UserDto} from "./user.dto.";
import {SESSION_STORAGE} from "../system/web-storage/web-storage.provider";
import {StorageService} from "../system/web-storage/storage.service";

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor(@Inject(SESSION_STORAGE) private storageService: StorageService ) { }


    signOut(): void {
        this.storageService.clear();
    }

    public saveToken(token: string): void {
      this.storageService.remove(TOKEN_KEY);
      this.storageService.set(TOKEN_KEY, token);
    }

    public getToken(): string | null {
      return this.storageService.get(TOKEN_KEY);
    }

    public saveUser(user: UserDto): void {
      this.storageService.remove(USER_KEY);
      this.storageService.set(USER_KEY, JSON.stringify(user));
    }

    public getUser(): UserDto | null {
        const userDtoStr = this.storageService.get(USER_KEY);
        if(! userDtoStr){
            return null;
        }
        return JSON.parse(userDtoStr);
    }

    public removeToken(): void {
      this.storageService.remove(TOKEN_KEY);
    }
    public removeUser(): void {
      this.storageService.remove(USER_KEY);
    }

}
