import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable} from "rxjs";
import {AnnotatedResult} from "./annotated-result";

@Injectable()//Providing this service only in ner module
export class ResultsDataService {

    results: BehaviorSubject<AnnotatedResult[]>;

    constructor() {
        this.results = new BehaviorSubject<AnnotatedResult[]>(new Array<AnnotatedResult>());
    }

    getResults(): Observable<AnnotatedResult[]> {
        return this.results;
    }

    addResult(data: AnnotatedResult) {
        let currentResult: AnnotatedResult[] = this.results.getValue();
        currentResult.push(data);
        this.results.next(currentResult);
    }

    deleteResult(beginIdx: number, endIdx: number) {
        let currentResult: AnnotatedResult[] = this.results.getValue();
        let filteredResults = currentResult.filter(value => {
            return value.begin != beginIdx && value.end != endIdx;
        });
        this.results.next(filteredResults);
    }

    dataLength() {
        return this.results.getValue().length;
    }


}
