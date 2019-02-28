import {Injectable} from "@angular/core";
import {Observable, of} from "rxjs";
import {AnnotatedResult} from "./annotated-result";

@Injectable({
  providedIn: 'root'
})
export class ResultsDataService {

  results: AnnotatedResult[];

  constructor() {
    this.results = new Array<AnnotatedResult>();
  }

  getData(): Observable<AnnotatedResult[]> {
    return of<AnnotatedResult[]>(this.results);
  }

  addResult(data: AnnotatedResult) {
    this.results.push(data);
  }

  deletePost(beginIdx: number, endIdx: number) {
    this.results = this.results.filter(value => {
       return value.begin != beginIdx && value.end != endIdx;
    });
  }

  dataLength() {
    return this.results.length;
  }
}
