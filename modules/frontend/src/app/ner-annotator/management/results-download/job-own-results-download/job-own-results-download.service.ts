import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JobOwnResultsDownloadService {

    public static readonly DOWNLOAD_BLIOU_FORMAT = '/ner/results/export/to/file';
    public static readonly DOWNLOAD_IN_JSON_SPACY_COMPATIBLE_FORMAT = '/ner/results/export/to/spacy/compatible/json/file';

    constructor(private httpClient: HttpClient) { }

    public downloadBLIOU(jobId: number): Observable<Blob>{
        return this.httpClient.get(JobOwnResultsDownloadService.DOWNLOAD_BLIOU_FORMAT, {
            params: {
              jobId: jobId.toString()
            },
            responseType: "blob"
        });
    }

    public downloadSpacyJson(jobId: number): Observable<Blob> {
        return this.httpClient.get(JobOwnResultsDownloadService.DOWNLOAD_IN_JSON_SPACY_COMPATIBLE_FORMAT, {
            params: {
                jobId: jobId.toString()
            },
            responseType: "blob"
        });
    }

}
