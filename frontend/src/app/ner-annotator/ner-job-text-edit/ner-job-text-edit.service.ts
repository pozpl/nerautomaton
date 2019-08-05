import {Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";
import {Injectable} from "@angular/core";

@Injectable()
export class NerJobTextEditService {

    constructor(private httpClient: HttpClient){
    }

    getTextItem(textItemId: number): Observable<NerJobTextDto> {
        return this.httpClient.get<NerJobTextDto>('/ner/text/edit/get', {
            params: new HttpParams().set('id', textItemId.toString()),
            responseType: 'json'
        });
    }

    /**
     * save text
     * @param nerJobTextDto
     */
    save(nerJobTextDto: NerJobTextDto):Observable<NerJobTextSaveStatus>{
        return this.httpClient.post<NerJobTextSaveStatus>('/ner/text/edit/save', nerJobTextDto, {
            responseType: 'json',
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        });
    }

    delete(textId: number):Observable<void>{
        return this.httpClient.delete<void>('/ner/text/edit/delete', {
            params: new HttpParams().set('id', textId.toString()),
            responseType: 'json',
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        });
    }
}


export class NerJobTextSaveStatus {
    nerJobDto: NerJobTextDto;

    status: string;

    errorCode: string;
}
