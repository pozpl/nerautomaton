import {Injectable} from "@angular/core";
import {NerJobTextDto} from "./ner-job-text.dto";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";


@Injectable()
export class NerJobTextAccessService {

    constructor(private httpClient: HttpClient){

    }

    listText(jobId: number, page:number):Observable<NerJobTextDto>{
        return this.httpClient.get<NerJobTextDto>("/ner/text/list", {
            params: new HttpParams().set('page', page.toString())
                        .append('jobId', jobId.toString()),
            responseType: 'json'
        })
    }
}
