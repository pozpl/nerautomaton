import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {NerJobDto} from "./ner-job.dto";
import {NerJobSaveStatus} from "./ner-job-save-status.dto";
import {Page} from "../../../shared-components/page";

@Injectable()
export class NerJobsService {

    constructor(private http: HttpClient) {
    }

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    getJobs(page: number): Observable<Page<NerJobDto>> {
        return this.http.get<Page<NerJobDto>>('/ner/edit/list', {
            params: {
                page: page.toString()
            }
        });
    }

    getJob(jobId: number): Observable<NerJobDto> {
        return this.http.get<NerJobDto>('/ner/edit/get', {
            params: {
                id: jobId.toString()
            }
        });
    }

    saveJob(jobDto: NerJobDto):Observable<NerJobSaveStatus>{
        return this.http.post<NerJobSaveStatus>('/ner/edit/save', jobDto);
    }

    deleteJob(jobId: number):Observable<void>{
        return this.http.delete<void>('/ner/edit/delete', {
            params: {
                id: jobId.toString()
            }
        });
    }
}

