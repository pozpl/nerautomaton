import {DataSource} from "@angular/cdk/table";
import {ResultsDataService} from "./results-data.service";
import {Observable} from "rxjs";
import {AnnotatedResult} from "./annotated-result";

export class ResultsDataSource extends DataSource<any> {
    constructor(private dataService: ResultsDataService) {
        super();
    }

    connect(): Observable<AnnotatedResult[]> {
        return this.dataService.getResults();
    }

    disconnect() {
    }
}
