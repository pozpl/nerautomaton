import {Component, OnDestroy, OnInit} from '@angular/core';
import {NerJobsService} from "./ner-jobs.service";
import {NerJobDto} from "./ner-job.dto";
import {DataSource} from "@angular/cdk/table";
import {Observable, Subject} from "rxjs";
import {map, takeUntil} from "rxjs/operators";
import {Router} from "@angular/router";
import {Page} from "../../../shared-components/page";
import {AuthService} from "../../../auth/auth.service";

@Component({
    selector: 'app-ner-jobs',
    templateUrl: './ner-jobs.component.html',
    styleUrls: ['./ner-jobs.component.scss']
})
export class NerJobsComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject();

    pagedContent: Page<NerJobDto>;

    //Things related to JObs table
    displayedColumns = ['name', 'dateCreated', 'delete'];


    constructor(public auth: AuthService,
                public nerJobsService: NerJobsService,
                private router: Router) {
    }

    ngOnInit() {

        this.nerJobsService
            .getJobs(1)
            .pipe(takeUntil(this.unsubscribe))
            .subscribe((page) => {
            this.pagedContent = page;
        });
    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

    goToAddJob() {
        this.router.navigateByUrl("/ner/job/edit/");
    }

    goToEditJob(jobId: number){
        this.router.navigate(["/ner/job/edit/", jobId])
    }

    deleteJob(job: NerJobDto){
        this.nerJobsService.deleteJob(job.id).subscribe(() =>{
           console.log("Job deleted");
        });
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
