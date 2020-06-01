import {Component, OnDestroy, OnInit} from '@angular/core';
import {NerAnnotationDataService} from "../data/ner-annotation-data.service";
import {NerTextAnnotationDto} from "../data/ner-text-annotation.dto";
import {ActivatedRoute} from "@angular/router";
import {flatMap, map, takeUntil} from "rxjs/operators";
import {forkJoin, Subject} from "rxjs";
import {NerLabelsAccessService} from "../data/ner-labels-access.service";
import {LabelDto} from "../../management/ner-jobs/label.dto";


@Component({
    selector: 'app-ner-annotation-page',
    templateUrl: './ner-annotation-page.component.html',
    styleUrls: ['./ner-annotation-page.component.scss']
})
export class NerAnnotationPageComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject<void>();

    private unprocessedTexts: NerTextAnnotationDto[];
    private jobId?: number;
    private labels: LabelDto[];

    public activeText: NerTextAnnotationDto;

    constructor(private annotationDataService: NerAnnotationDataService,
                private nerLabelsAccessService: NerLabelsAccessService,
                private activeRoute: ActivatedRoute) {
    }

    ngOnInit() {
        this.activeRoute
            .params
            .pipe(
                takeUntil(this.unsubscribe),
                flatMap(params => {
                    this.jobId = params['jobId'];
                    return forkJoin({
                       itemsToProcess: this.annotationDataService.getUnprocessed(this.jobId),
                       labels: this.nerLabelsAccessService.getLabelsForJob(this.jobId)
                    });
                })
            )
            .subscribe(itemsAndLabels => {
                this.unprocessedTexts = itemsAndLabels.itemsToProcess;
                this.labels = itemsAndLabels.labels;
            });

    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

    loadNextTextsBatch(){
        this.annotationDataService.getUnprocessed(this.jobId)
            .subscribe(textItems => {
               this.unprocessedTexts = textItems
            });
    }
}
