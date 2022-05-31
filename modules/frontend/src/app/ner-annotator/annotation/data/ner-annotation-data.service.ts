import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {NerTextAnnotationDto} from "./ner-text-annotation.dto";
import {Page} from "../../../shared-components/page";
import {NerTextAnnotationEditResultDto} from "./ner-text-annotation-edit-result.dto";

@Injectable({
    providedIn: 'root'
})
export class NerAnnotationDataService {

    public static readonly GET_UNPROCESSED = 'ner/annotation/text/tasks/get/unprocessed'
    public static readonly GET_PROCESSED = 'ner/annotation/text/tasks/list/processed'
    public static readonly SAVE_RESULT = 'ner/annotation/text/annotation/save'

    constructor(private httpClient: HttpClient) {
    }


    /**
     * Get unprocessed tasks
     */
    getUnprocessed(jobId: number): Observable<NerTextAnnotationDto[]>{
        return this.httpClient.get<NerTextAnnotationDto[]>(NerAnnotationDataService.GET_UNPROCESSED, {
            params: {
                jobId: jobId.toString()
            }
        });
    }

    getProcessed(jobId: number, page: number): Observable<Page<NerTextAnnotationDto>> {
        return this.httpClient.get<Page<NerTextAnnotationDto>>(NerAnnotationDataService.GET_PROCESSED, {
            params: {
                jobId: jobId.toString(),
                page: page.toString()
            }
        });
    }

    saveAnnotation(textAnnotation: NerTextAnnotationDto): Observable<NerTextAnnotationEditResultDto> {
        return this.httpClient.post<NerTextAnnotationEditResultDto>(NerAnnotationDataService.SAVE_RESULT, textAnnotation);
    }


}
