import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NerJobsService} from "../ner-jobs.service";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";

@Component({
    selector: 'ner-job-edit',
    templateUrl: './ner-job-edit.component.html',
    styleUrls: ['./ner-job-edit.component.scss']
})
export class NerJobEditComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject();
    jobId: number;

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
                        .subscribe()
                }
            });
    }

    ngOnDestroy() {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

}
