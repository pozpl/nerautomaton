import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NerJobsService} from "../ner-jobs.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {NerJobDto} from "../ner-job.dto";

@Component({
    selector: 'ner-job-edit',
    templateUrl: './ner-job-edit.component.html',
    styleUrls: ['./ner-job-edit.component.scss']
})
export class NerJobEditComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject();
    jobId: number;
    jobDto: NerJobDto;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private nerJobService: NerJobsService) {
    }

    ngOnInit() {
        this.route
            .params
            .pipe(takeUntil(this.unsubscribe))
            .subscribe(params => {
                this.jobId = params['id'];
                if (this.jobId != null) {
                    this.nerJobService.getJob(this.jobId)
                        .pipe(takeUntil(this.unsubscribe))
                        .subscribe(jobDto => {
                            this.jobDto = jobDto;
                        })
                }else{
                    this.jobDto = new NerJobDto();
                }

            });
    }

    ngOnDestroy() {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }


    saveJob() {
        this.nerJobService.saveJob(this.jobDto)
            .pipe(takeUntil(this.unsubscribe))
            .subscribe(savedJobStatus => {
                if(savedJobStatus.status == 'OK'){
                    this.jobDto = savedJobStatus.nerJobDto;
                }
            });
    }

}
