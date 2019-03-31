import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {NerJobDto} from "./ner-job.dto";

@Injectable()
export class NerJobsService {

    constructor(private http: HttpClient) {
    }


    getJobs(page: number): Observable<Page<NerJobDto>> {
        return this.http.get<Page<NerJobDto>>('/ner/edit/list', {
            params: new HttpParams().set('page', page.toString()),
            responseType: 'json'
        });
    }

    getJob(jobId): Observable<NerJobDto> {
        return this.http.get<NerJobDto>('/ner/edit/list', {
            params: new HttpParams().set('id', jobId),
            responseType: 'json'
        });
    }
}


export interface Page<T> {
    content: Array<T>;
    pagable: any
}
