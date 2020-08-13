import {Component, OnDestroy, OnInit} from '@angular/core';
import {NerJobsService} from "./ner-jobs.service";
import {NerJobDto} from "./ner-job.dto";
import {DataSource} from "@angular/cdk/table";
import {Observable, Subject} from "rxjs";
import {flatMap, map, takeUntil} from "rxjs/operators";
import {ActivatedRoute, Router} from "@angular/router";
import {Page} from "../../../shared-components/page";
import {AuthService} from "../../../auth/auth.service";
import {PageEvent} from "@angular/material/paginator";

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

    page = 0;

    constructor(public auth: AuthService,
                public nerJobsService: NerJobsService,
                private router: Router,
                private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {

        this.activatedRoute.queryParamMap
            .pipe(
                takeUntil(this.unsubscribe),
                map(queryParams => {
                    this.page = Number(queryParams.get('page')) || 0;
                    return this.page;
                }),
                flatMap(page => {
                    return this.nerJobsService.getJobs(page)
                })
            ).subscribe(jobs => {
                this.pagedContent = jobs;
            });

    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

    goToAddJob() {
        this.router.navigateByUrl("/ner/job/edit/");
    }

    goToEditJob(jobId: number) {
        this.router.navigate(["/ner/job/edit/", jobId])
    }

    deleteJob(job: NerJobDto) {
        this.nerJobsService.deleteJob(job.id).subscribe(() => {
            this.pagedContent.content = this.pagedContent.content.filter(jobDto => job.id !== jobDto.id );
        });
    }

    pageChanged(event: PageEvent){
        this.page = event.pageIndex;
        this.router.navigate([], {
            queryParams: {
                page: this.page.toString()
            }
        });
    }

}


export class NerJobsDataSource extends DataSource<any> {
    constructor(private dataService: NerJobsService) {
        super();
    }

    connect(): Observable<NerJobDto[]> {
        return this.dataService.getJobs(0)
            .pipe(map(pageContext => pageContext.content));
    }

    disconnect() {
    }
}
