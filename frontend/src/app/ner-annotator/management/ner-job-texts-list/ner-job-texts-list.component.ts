import {Component, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {NerJobTextDto} from "./ner-job-text.dto";
import {NerJobDto} from "../ner-jobs/ner-job.dto";
import {NerJobTextAccessService} from "./ner-job-text-access.service";
import {BehaviorSubject, Observable, of} from "rxjs";
import {MatPaginator, PageEvent} from "@angular/material/paginator";
import {finalize, flatMap} from "rxjs/operators";
import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {NerJobTextEditService} from "../ner-job-text-edit/ner-job-text-edit.service";
import {NerJobTextEditModalService} from "../ner-job-text-edit/ner-job-text-edit-modal.service";

@Component({
    selector: 'ner-job-texts-list',
    templateUrl: './ner-job-texts-list.component.html',
    styleUrls: ['./ner-job-texts-list.component.scss'],
    providers: [
        NerJobTextAccessService,
        NerJobTextEditService
    ]
})
export class NerJobTextsListComponent implements OnInit, OnChanges {

    @Input() job: NerJobDto;

    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

    public dataSource: TextItemsDatasource;
    private page = 1;

    displayedColumns = ["edit", "text", "delete"];


    constructor(private textAccessService: NerJobTextAccessService,
                private textEditService: NerJobTextEditService,
                private nerJobTextEditModalService: NerJobTextEditModalService) {
        this.dataSource = new TextItemsDatasource(this.textAccessService);
    }

    ngOnInit() {
    }


    deleteText(textDto: NerJobTextDto) {
        this.textEditService.delete(textDto.id)
            .subscribe(() => this.dataSource.deleteFromList(textDto));
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (this.job !== null) {
            this.dataSource.listText(this.job.id, this.page);
        }
    }

    public pageChanged(event: PageEvent) {
        this.page = event.pageIndex;
        this.dataSource.listText(this.job.id, event.pageIndex);
    }

    editText(textDto: NerJobTextDto) {
        this.nerJobTextEditModalService.openForEdit(textDto)
            .pipe(flatMap(dto => {
                    if (dto != null) {
                        return this.textEditService.save(textDto);
                    }
                    return of(null);
                }
            ))
            .subscribe(dto => {
                    if (dto !== null) {
                        this.dataSource.listText(this.job.id, this.page);
                    }

                }
            );
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

    addToTheTop(nerJobText: NerJobTextDto) {
        const existingList = this.documentsSubject.getValue();
        existingList.unshift(nerJobText);
        this.documentsSubject.next(existingList);
    }

    deleteFromList(nerJobTextDto: NerJobTextDto) {
        const existingList = this.documentsSubject.getValue();
        const filteredList = existingList.filter(el => el.id != nerJobTextDto.id);
        this.documentsSubject.next(filteredList);
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
                this.numberDocuments = result.size;
            });
    }

}
