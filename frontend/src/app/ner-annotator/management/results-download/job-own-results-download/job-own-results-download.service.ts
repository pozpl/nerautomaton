import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JobOwnResultsDownloadService {

    public static readonly DOWNLOAD_FOR_OWNER = '/ner/results/export/to/file';

    constructor(private httpClient: HttpClient) { }

    public downloadForOwner(jobId: number): Observable<Blob>{
        return this.httpClient.get(JobOwnResultsDownloadService.DOWNLOAD_FOR_OWNER, {
            params: {
              jobId: jobId.toString()
            },
            responseType: "blob"
        });
    }

}
