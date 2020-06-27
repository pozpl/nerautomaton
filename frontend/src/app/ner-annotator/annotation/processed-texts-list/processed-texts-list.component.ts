import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProcessedTextsDatasource} from "./processed-texts-datasource";
import {NerAnnotationDataService} from "../data/ner-annotation-data.service";
import {ActivatedRoute, Router} from '@angular/router';
import {forkJoin, Subject, zip} from "rxjs";
import {concatMap, map, takeUntil} from "rxjs/operators";
import {PageEvent} from "@angular/material/paginator";
import {NerJobsService} from "../../management/ner-jobs/ner-jobs.service";
import {NerJobDto} from "../../management/ner-jobs/ner-job.dto";
import {NerTextAnnotationDto} from "../data/ner-text-annotation.dto";
import {NerLabelsAccessService} from "../data/ner-labels-access.service";
import {LabelDto} from "../../management/ner-jobs/label.dto";

@Component({
    selector: 'app-processed-texts-list',
    templateUrl: './processed-texts-list.component.html',
    styleUrls: ['./processed-texts-list.component.scss']
})
export class ProcessedTextsListComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject<void>();

    public dataSource: ProcessedTextsDatasource;
    public page = 0;
    private jobId: number | undefined;
    public job?: NerJobDto;
    public labels?: LabelDto[];

    displayedColumns = ["text", "review"];

    constructor(private nerAnnotationDataService: NerAnnotationDataService,
                private router: Router,
                private activeRoute: ActivatedRoute,
                private nerJobsService: NerJobsService,
                private nerLabelsAccessService: NerLabelsAccessService) {
        this.dataSource = new ProcessedTextsDatasource(nerAnnotationDataService);
    }

    ngOnInit() {

        zip(
            this.activeRoute.paramMap,
            this.activeRoute.queryParamMap
        ).pipe(
            takeUntil(this.unsubscribe),
            map(paramsAndQuery => {
                this.jobId = Number(paramsAndQuery[0].get('jobId'));
                this.page = Number(paramsAndQuery[1].get('page')) || 0;

                return this.jobId;
            }),
            concatMap(jobId => {
                return forkJoin([
                    this.nerJobsService.getJob(this.jobId),
                    this.nerLabelsAccessService.getLabelsForJob(this.jobId)
                ]);
            })
        ).subscribe(jobAndLabels => {
              this.job = jobAndLabels[0];
              this.labels = jobAndLabels[1];
              this.dataSource.list(this.jobId, this.page);
        });

    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

    public pageChanged(event: PageEvent) {
        this.page = event.pageIndex;
        this.router.navigate([this.jobId], {
            queryParams:{
                page: this.page.toString()
            }
        });
        // if (this.jobId) {
        //     this.dataSource.list(this.jobId, event.pageIndex);
        // }
    }

    public reviewTextAnnotation(annotatedText: NerTextAnnotationDto) {
        this.router.navigateByUrl('/ner/review/processed/text', {
            state: {
                annotatedText: annotatedText,
                labels: this.labels
            }
        });
    }

}
