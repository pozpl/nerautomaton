import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {Page} from "../ner-jobs/ner-jobs.service";
import {NerJobTextDto} from "./ner-job-text.dto";
import {NerJobDto} from "../ner-jobs/ner-job.dto";
import {NerJobTextAccessService} from "./ner-job-text-access.service";
import {BehaviorSubject, Observable, of} from "rxjs";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {catchError, finalize, map} from "rxjs/operators";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";

@Component({
    selector: 'ner-job-texts-list',
    templateUrl: './ner-job-texts-list.component.html',
    styleUrls: ['./ner-job-texts-list.component.scss'],
    providers: [NerJobTextAccessService]
})
export class NerJobTextsListComponent implements OnInit, OnChanges {

    @Input() job: NerJobDto;

    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

    public dataSource: TextItemsDatasource;

    displayedColumns = ["seqNo", "text", "delete"];

    // pagedContent$: Observable<NerJobTextDto[]>;
    // page: number = 1;

    constructor(private textAccessService: NerJobTextAccessService) {
        this.dataSource = new TextItemsDatasource(this.textAccessService);
    }

    ngOnInit() {
        // this.dataSource = new TextItemsDatasource(this.textAccessService);
        // this.dataSource.paginator = this.paginator;
    }


    deleteText(textDto: NerJobTextDto) {

    }

    ngOnChanges(changes: SimpleChanges): void {
        if (this.job !== null) {
            // this.pagedContent$ = this.textAccessService
            //     .listText(this.job.id, this.page)
            //     .pipe(map(page => page.content));
            this.dataSource.listText(this.job.id, 1);
        }
    }

    public pageChanged(event: PageEvent) {
        this.dataSource.listText(this.job.id, event.pageIndex);
    }
}


export class TextItemsDatasource implements DataSource<NerJobTextDto> {

    numberDocuments: number;
    private documentsSubject = new BehaviorSubject<NerJobTextDto[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public loading$ = this.loadingSubject.asObservable();

    constructor(private textAccessService: NerJobTextAccessService) {

    }

    connect(collectionViewer: CollectionViewer): Observable<NerJobTextDto[] | ReadonlyArray<NerJobTextDto>> {
        return this.documentsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.documentsSubject.complete();
        this.loadingSubject.complete();
    }

    listText(jobId: number, page: number) {
        this.loadingSubject.next(true);
        this.textAccessService.listText(jobId, page)
            .pipe(
                 // catchError(() => of(new Array<NerJobTextDto>[])),
                finalize(() => this.loadingSubject.next(false))
            ).subscribe(
            result => {
                this.documentsSubject.next(result.content);
                this.numberDocuments = result.totalElements;
            });
    }

}
