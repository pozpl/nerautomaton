import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth.service";
import {MatDialog} from "@angular/material";
import {NerJobsService, Page} from "./ner-jobs.service";
import {NerJobDto} from "./ner-job.dto";
import {DataSource} from "@angular/cdk/table";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

@Component({
    selector: 'app-ner-jobs',
    templateUrl: './ner-jobs.component.html',
    styleUrls: ['./ner-jobs.component.scss']
})
export class NerJobsComponent implements OnInit {

    currentPageContent: NerJobDto[];
    pagedContent: Page<NerJobDto>;

    //Things related to JObs table
    displayedColumns = ['dateCreated', 'name', 'delete'];
    nerJobsDataSource: NerJobsDataSource;

    constructor(public auth: AuthService,
                public nerJobsService: NerJobsService,
                public dialog: MatDialog) {
    }

    ngOnInit() {

        this.nerJobsService.getJobs(1).subscribe((page) => {
            this.pagedContent = page;
        });
    }

    goToAddEditJob() {

    }

}



export class NerJobsDataSource extends DataSource<any> {
    constructor(private dataService: NerJobsService) {
        super();
    }

    connect(): Observable<NerJobDto[]> {
        return this.dataService.getJobs(1)
            .pipe(map(pageContext => pageContext.content));
    }

    disconnect() {
    }
}
