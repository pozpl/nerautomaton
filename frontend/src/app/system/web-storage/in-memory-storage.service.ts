import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InMemoryStorageService {

    private readonly storage: Map<string, string> = new Map<string, string>();

    constructor() {
    }

    public has(key: string): boolean {
        return this.storage.has(key);
    }

    public remove(key: string): void {
        this.storage.delete(key);
    }

    public clear(): void {
        this.storage.clear();
    }

    public get(key: string): string | undefined {
        if (!this.storage.has(key)) {
            return undefined;
        }

        return this.storage.get(key)!;
    }

    public set(key: string, value: string): void {
        this.storage.set(key, value);
    }
}
