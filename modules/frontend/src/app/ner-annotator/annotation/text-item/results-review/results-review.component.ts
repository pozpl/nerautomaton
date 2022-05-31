import {Component, OnInit} from '@angular/core';
import {ResultsDataSource} from "../results-data-source";
import {ResultsDataService} from "../results-data.service";


@Component({
    selector: 'item-results-review',
    templateUrl: './results-review.component.html',
    styleUrls: ['./results-review.component.scss']
})
export class ResultsReviewComponent implements OnInit {

    resultsDisplayedColumns = ['tokens', 'annotation', 'delete'];
    resultsDataSource: ResultsDataSource;

    constructor(private resultsDataService: ResultsDataService) {

        this.resultsDataSource = new ResultsDataSource(resultsDataService);
    }

    ngOnInit() {
    }

    // onResultsChange() {
    //     this.resultsDataService.getResults().subscribe(resultsDataService => {
    //         this.resultsDataSource = new ResultsDataSource(this.resultsDataService);
    //     });
    // }

    deleteResult(beginIdx, endIdx) {
        this.resultsDataService.deleteResult(beginIdx, endIdx);
        this.resultsDataSource = new ResultsDataSource(this.resultsDataService);
    }

}
