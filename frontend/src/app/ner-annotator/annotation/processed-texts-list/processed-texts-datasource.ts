import {DataSource} from "@angular/cdk/table";
import {CollectionViewer} from "@angular/cdk/collections";
import {BehaviorSubject, Observable} from "rxjs";
import {NerTextAnnotationDto} from "../data/ner-text-annotation.dto";
import {NerAnnotationDataService} from "../data/ner-annotation-data.service";
import {finalize} from "rxjs/operators";

export class ProcessedTextsDatasource extends DataSource<NerTextAnnotationDto>{

    numberDocuments: number;
    private documentsSubject = new BehaviorSubject<NerTextAnnotationDto[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public loading$ = this.loadingSubject.asObservable();

    constructor(private annotationDataService: NerAnnotationDataService) {
        super();
    }

    connect(collectionViewer: CollectionViewer): Observable<NerTextAnnotationDto[] | ReadonlyArray<NerTextAnnotationDto>> {
        return this.documentsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.documentsSubject.complete();
        this.loadingSubject.complete();
    }

    list(jobId: number, page: number) {
        this.loadingSubject.next(true);
        this.annotationDataService.getProcessed(jobId, page)
            .pipe(
                finalize(() => this.loadingSubject.next(false))
            ).subscribe(
            result => {
                this.documentsSubject.next(result.content);
                this.numberDocuments = result.size;
            });
    }

}
