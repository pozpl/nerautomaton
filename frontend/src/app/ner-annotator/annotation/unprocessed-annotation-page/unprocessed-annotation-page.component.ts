import {Component, OnDestroy, OnInit} from '@angular/core';
import {NerAnnotationDataService} from "../data/ner-annotation-data.service";
import {NerTextAnnotationDto} from "../data/ner-text-annotation.dto";
import {ActivatedRoute, Router} from "@angular/router";
import {flatMap, takeUntil} from "rxjs/operators";
import {forkJoin, Subject} from "rxjs";
import {NerLabelsAccessService} from "../data/ner-labels-access.service";
import {LabelDto} from "../../management/ner-jobs/label.dto";

@Component({
    selector: 'unprocessed-annotation-page',
    templateUrl: './unprocessed-annotation-page.component.html',
    styleUrls: ['./unprocessed-annotation-page.component.scss']
})
export class UnprocessedAnnotationPageComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject<void>();

    private unprocessedTexts: NerTextAnnotationDto[];
    private jobId?: number;
    public labels: LabelDto[];
    public dataLoading = false;

    public activeText?: NerTextAnnotationDto | null;

    constructor(private annotationDataService: NerAnnotationDataService,
                private nerLabelsAccessService: NerLabelsAccessService,
                private activeRoute: ActivatedRoute,
                private router: Router) {
    }

    ngOnInit() {
        this.dataLoading = true;
        this.activeRoute
            .params
            .pipe(
                takeUntil(this.unsubscribe),
                flatMap(params => {
                    this.jobId = params['jobId'];
                    return forkJoin({
                        itemsToProcess: this.annotationDataService.getUnprocessed(this.jobId || 0),
                        labels: this.nerLabelsAccessService.getLabelsForJob(this.jobId || 0)
                    });
                })
            )
            .subscribe(itemsAndLabels => {
                if (itemsAndLabels) {
                    this.unprocessedTexts = itemsAndLabels.itemsToProcess;
                    this.labels = itemsAndLabels.labels;
                    if (this.unprocessedTexts.length > 0) {
                        this.activeText = this.unprocessedTexts.shift();
                    }
                }

                this.dataLoading = false;
            });

    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }


    public processFinishedAnnotation(processedText: NerTextAnnotationDto) {

        this.annotationDataService.saveAnnotation(processedText).subscribe(
            editResult => {
                this.assignNextTextsForProcessing();
            });

    }

    private assignNextTextsForProcessing() {
        if (this.unprocessedTexts.length > 0) {
            if (this.unprocessedTexts.length > 0) {
                this.activeText = this.unprocessedTexts.shift();
            }
        } else if(this.jobId) {
            this.annotationDataService.getUnprocessed(this.jobId)
                .pipe(takeUntil(this.unsubscribe))
                .subscribe(unprocessedTexts => {
                    this.unprocessedTexts = unprocessedTexts;
                    if (this.unprocessedTexts && this.unprocessedTexts.length > 0) {
                        this.activeText = this.unprocessedTexts.shift();
                    }else{
                        this.activeText = null;
                    }
                });
        }
    }

    public goToTaskList(){
        this.router.navigate(['ner/user/tasks'])
    }

    public reviewProcessedTexts(){
        this.router.navigate(['ner/job/annotate/processed/', this.jobId])
    }

}
