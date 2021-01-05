import {InMemoryStorageService} from "./in-memory-storage.service";
import {WebStorageService} from "./web-storage.service";
import {StorageService} from "./storage.service";
import {InjectionToken} from "@angular/core";


export function sessionStorageFactory(): StorageService {
    if (isSessionStorageAvailable()) {
        return new WebStorageService(sessionStorage);
    }

    return new InMemoryStorageService();
}

/** Injection token for the session storage service. */
export const SESSION_STORAGE = new InjectionToken<StorageService>(
    'SESSION_STORAGE',
    { providedIn: 'root', factory: sessionStorageFactory }
);

export function localStorageFactory(): StorageService {
    if (isLocalStorageAvailable()) {
        return new WebStorageService(localStorage);
    }

    return new InMemoryStorageService();
}

/** Injection token for the local storage service. */
export const LOCAL_STORAGE = new InjectionToken<StorageService>(
    'LOCAL_STORAGE',
    { providedIn: 'root', factory: localStorageFactory }
);




export function isStorageAvailable(storage: Storage): boolean {
    // Check if storage is available.
    if (!storage) {
        return false;
    }

    // Check if the storage can actually be accessed.
    try {
        const now = Date.now();
        const testItemKey = `storage-test-entry-${now}`;
        const testItemValue = `storage-test-value-${now}`;
        storage.setItem(testItemKey, testItemValue);
        const retrievedItemValue = storage.getItem(testItemKey);
        storage.removeItem(testItemKey);

        return retrievedItemValue === testItemValue;
    } catch (error) {
        return false;
    }
}

export function isSessionStorageAvailable(): boolean {
    try {
        if (typeof sessionStorage as any !== 'undefined') {
            return isStorageAvailable(sessionStorage);
        }
    } catch {}

    return false;
}

export function isLocalStorageAvailable(): boolean {
    try {
        if (typeof localStorage as any !== 'undefined') {
            return isStorageAvailable(localStorage);
        }
    } catch {}

    return false;
}
