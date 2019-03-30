import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../auth.service";
import {NerJobsService, Page} from "./ner-jobs.service";
import {NerJobDto} from "./ner-job.dto";
import {DataSource} from "@angular/cdk/table";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {Router} from "@angular/router";

@Component({
    selector: 'app-ner-jobs',
    templateUrl: './ner-jobs.component.html',
    styleUrls: ['./ner-jobs.component.scss']
})
export class NerJobsComponent implements OnInit {

    
    pagedContent: Page<NerJobDto>;

    //Things related to JObs table
    displayedColumns = ['dateCreated', 'name', 'delete'];
    nerJobsDataSource: NerJobsDataSource;

    constructor(public auth: AuthService,
                public nerJobsService: NerJobsService,
                private router: Router) {
    }

    ngOnInit() {

        this.nerJobsService.getJobs(1).subscribe((page) => {
            this.pagedContent = page;
        });
    }

    goToAddJob() {
        this.router.navigateByUrl("/ner/job/edit/");
    }

    goToEditJob(jobId: number){
        this.router.navigate(["/ner/job/edit/", jobId])
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
