import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {NerJobDto} from "./ner-job.dto";
import {Page} from "../../shared-components/page";
import {NerJobSaveStatus} from "./ner-job-save-status.dto";

@Injectable()
export class NerJobsService {

    constructor(private http: HttpClient) {
    }

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    getJobs(page: number): Observable<Page<NerJobDto>> {
        return this.http.get<Page<NerJobDto>>('/ner/edit/list', {
            params: new HttpParams().set('page', page.toString()),
            responseType: 'json'
        });
    }

    getJob(jobId): Observable<NerJobDto> {
        return this.http.get<NerJobDto>('/ner/edit/get', {
            params: new HttpParams().set('id', jobId),
            responseType: 'json'
        });
    }

    saveJob(jobDto: NerJobDto):Observable<NerJobSaveStatus>{
        return this.http.post<NerJobSaveStatus>('/ner/edit/save', jobDto, {
            responseType: 'json',
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        });
    }

    deleteJob(jobId):Observable<void>{
        return this.http.delete<void>('/ner/edit/delete', {
            params: new HttpParams().set('id', jobId),
            responseType: 'json',
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        });
    }
}

