import { Injectable } from '@angular/core';
import {StorageService} from "./storage.service";

@Injectable({
  providedIn: 'root'
})
export class WebStorageService implements StorageService{


    constructor(private readonly storage: Storage) {
    }

    public has(key: string): boolean {
        return this.storage.getItem(key) !== null;
    }

    public remove(key: string): void {
        this.storage.removeItem(key);
    }


    public clear(): void {
        this.storage.clear();
    }

    public get(key: string): string | undefined {
        const value = this.storage.getItem(key);

        return value !== null ? value : undefined;
    }

    public set(key: string, value: string): void {
        return this.storage.setItem(key, value);
    }
}
