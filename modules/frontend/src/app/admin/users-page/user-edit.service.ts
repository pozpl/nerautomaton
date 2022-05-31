import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../../shared-components/page";
import {UserEditDto} from "./user-edit.dto";
import {UserSaveStatusDto} from "./user-save-status.dto";

@Injectable({
    providedIn: 'root'
})
export class UserEditService {

    constructor(private httpClient: HttpClient) {
    }

    public list(page: number): Observable<Page<UserEditDto>> {
        return this.httpClient.get<Page<UserEditDto>>('/admin/user/edit/list', {
            params: {
                page: page.toString()
            }
        });
    }

    public get(userId: number): Observable<UserEditDto> {
        return this.httpClient.get<UserEditDto>('/admin/user/edit/get', {
            params: {
                id: userId.toString()
            }
        });
    }

    public save(userEditDto: UserEditDto): Observable<UserSaveStatusDto>{
        return this.httpClient.post<UserSaveStatusDto>('/admin/user/edit/save', userEditDto);
    }

    public delete(userId: number): Observable<boolean>{
        return this.httpClient.delete<boolean>('/admin/user/edit/delete');
    }

}
