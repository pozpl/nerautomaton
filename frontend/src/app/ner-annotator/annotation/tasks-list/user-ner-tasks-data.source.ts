import {DataSource} from "@angular/cdk/table";
import {BehaviorSubject, Observable} from "rxjs";
import {finalize} from "rxjs/operators";
import {UserNerTaskDescriptionDto} from "./user-ner-task-description.dto";
import {UserNerTasksService} from "./user-ner-tasks.service";
import {CollectionViewer} from "@angular/cdk/collections";

export class UserNerTasksDataSource extends DataSource<UserNerTaskDescriptionDto> {


    constructor(private dataService: UserNerTasksService) {
        super();
    }

    numberDocuments: number;
    private documentsSubject = new BehaviorSubject<UserNerTaskDescriptionDto[]>([]);
    private loadingSubject = new BehaviorSubject<boolean>(false);
    public loading$ = this.loadingSubject.asObservable();


    connect(collectionViewer: CollectionViewer): Observable<UserNerTaskDescriptionDto[] | ReadonlyArray<UserNerTaskDescriptionDto>> {
        return this.documentsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.documentsSubject.complete();
        this.loadingSubject.complete();
    }


    list(page: number) {
        this.loadingSubject.next(true);
        this.dataService.getTasks(page)
            .pipe(
                finalize(() => this.loadingSubject.next(false))
            ).subscribe(
            result => {
                if(result.content) {
                    this.documentsSubject.next(result.content);
                }
                this.numberDocuments = result.size;
            });
    }
}
