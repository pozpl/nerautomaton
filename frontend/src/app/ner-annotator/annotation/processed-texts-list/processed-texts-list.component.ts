import {Component, OnDestroy, OnInit} from '@angular/core';
import {ProcessedTextsDatasource} from "./processed-texts-datasource";
import {NerAnnotationDataService} from "../data/ner-annotation-data.service";
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {PageEvent} from "@angular/material/paginator";

@Component({
    selector: 'app-processed-texts-list',
    templateUrl: './processed-texts-list.component.html',
    styleUrls: ['./processed-texts-list.component.scss']
})
export class ProcessedTextsListComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject<void>();

    public dataSource: ProcessedTextsDatasource;
    public page = 1;
    private jobId: number | undefined;

    displayedColumns = ["text", "review"];

    constructor(private nerAnnotationDataService: NerAnnotationDataService,
                private router: Router,
                private activeRoute: ActivatedRoute) {
        this.dataSource = new ProcessedTextsDatasource(nerAnnotationDataService);
    }

    ngOnInit() {
        this.activeRoute
            .params
            .pipe(takeUntil(this.unsubscribe))
            .subscribe(params => {
                this.jobId = params['jobId'];

                this.dataSource.list(this.jobId, this.page);
            });

    }

    ngOnDestroy(): void {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }

    public pageChanged(event: PageEvent) {
        this.page = event.pageIndex;
        if(this.jobId) {
            this.dataSource.list(this.jobId, event.pageIndex);
        }
    }

}
