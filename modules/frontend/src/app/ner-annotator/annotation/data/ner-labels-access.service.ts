import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {LabelDto} from "../../management/ner-jobs/label.dto";


@Injectable({
    providedIn: 'root'
})
export class NerLabelsAccessService {

    public static readonly GET_LABELS = 'ner/labels/get/for/job'

    constructor(private httpClient: HttpClient) {
    }

    getLabelsForJob(jobId: number) : Observable<LabelDto[]> {
        return this.httpClient.get<LabelDto[]>(NerLabelsAccessService.GET_LABELS, {
            params:{
                jobId: jobId.toString()
            }
        });
    }
}
