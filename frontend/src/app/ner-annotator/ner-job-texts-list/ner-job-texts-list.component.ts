import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {NerJobTextDto} from "./ner-job-text.dto";
import {NerJobDto} from "../ner-jobs/ner-job.dto";
import {NerJobTextAccessService} from "./ner-job-text-access.service";
import {BehaviorSubject, Observable} from "rxjs";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {finalize} from "rxjs/operators";
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


    constructor(private textAccessService: NerJobTextAccessService) {
        this.dataSource = new TextItemsDatasource(this.textAccessService);
    }

    ngOnInit() {
    }


    deleteText(textDto: NerJobTextDto) {

    }

    ngOnChanges(changes: SimpleChanges): void {
        if (this.job !== null) {
            this.dataSource.listText(this.job.id, 1);
        }
    }

    public pageChanged(event: PageEvent) {
        this.dataSource.listText(this.job.id, event.pageIndex);
    }

    onNewTextAdded(newTextItem: NerJobTextDto) {
        this.dataSource.addToTheTop(newTextItem);
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

    addToTheTop(nerJobText: NerJobTextDto){
        const existingList = this.documentsSubject.getValue();
        existingList.unshift(nerJobText);
        this.documentsSubject.next(existingList);
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
