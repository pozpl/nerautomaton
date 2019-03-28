import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {NerJobDto} from "./ner-job.dto";

@Injectable()
export class NerJobsService {

  constructor(private http: HttpClient) { }


  getJobs(): Observable<Page<NerJobDto>>{
      return this.http.get<Page<NerJobDto>>('/ner/edit/list');
  }
}


export interface Page<T> {
    content: Array<T>;
    pagable: any
}
