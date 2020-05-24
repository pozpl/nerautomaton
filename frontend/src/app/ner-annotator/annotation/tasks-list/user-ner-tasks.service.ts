import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UserNerTaskDescriptionDto} from "./user-ner-task-description.dto";
import {Page} from "../../../shared-components/page";

@Injectable({
    providedIn: 'root'
})
export class UserNerTasksService {

    public static readonly GET_TASKS_URL = 'ner/user/tasks/list';

    constructor(private httpClient: HttpClient) {
    }

    getTasks(page: number | undefined | null): Observable<Page<UserNerTaskDescriptionDto>> {
        const pageAdj = (page || 1).toString();
        return this.httpClient.get<Page<UserNerTaskDescriptionDto>>(UserNerTasksService.GET_TASKS_URL, {
            params: {
                page: pageAdj
            }
        });
    }


}
