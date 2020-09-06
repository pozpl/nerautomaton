import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {NerJobsService} from "../ner-jobs.service";
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {NerJobDto} from "../ner-job.dto";
import {DataSource} from "@angular/cdk/table";
import {CollectionViewer} from "@angular/cdk/collections";
import {LabelDto} from "../label.dto";

@Component({
    selector: 'ner-job-edit',
    templateUrl: './ner-job-edit.component.html',
    styleUrls: ['./ner-job-edit.component.scss']
})
export class NerJobEditComponent implements OnInit, OnDestroy {

    private unsubscribe: Subject<void> = new Subject();
    jobId: number;
    jobDto: NerJobDto;

    nerJobLabelsDisplayColumns  = ['name', 'description', 'delete'];
    jobLabelsDataSource: JobLabelsDataSource;


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
                        .subscribe(jobDto => {
                            this.jobDto = jobDto;
                            this.jobLabelsDataSource = new JobLabelsDataSource(this.jobDto.labels || []);
                        })
                }else{
                    this.jobDto = new NerJobDto();
                    this.jobLabelsDataSource = new JobLabelsDataSource(this.jobDto.labels || []);
                }

            });
    }

    ngOnDestroy() {
        this.unsubscribe.next();
        this.unsubscribe.complete();
    }


    saveJob() {
        this.nerJobService.saveJob(this.jobDto)
            .pipe(takeUntil(this.unsubscribe))
            .subscribe(savedJobStatus => {
                if(savedJobStatus.status == 'OK'){
                    this.jobDto = savedJobStatus.nerJobDto;
                }
            });
    }

    addLabel(){
        if(this.jobDto.labels == null){
            this.jobDto.labels = [];
        }
        this.jobDto.labels.push(new LabelDto());
        this.jobLabelsDataSource.updateLabels(this.jobDto.labels);
    }

    deleteLabel(labelDto: LabelDto){
        this.jobDto.labels = this.jobDto.labels || [];
        this.jobDto.labels = this.jobDto.labels.filter(el => el != labelDto);
        this.jobLabelsDataSource.updateLabels(this.jobDto.labels);
    }

}


export class JobLabelsDataSource implements DataSource<LabelDto> {

    private labelsSubject = new BehaviorSubject<LabelDto[]>([]);


    constructor(private labels: LabelDto[]) {
        this.labelsSubject.next(labels)
    }

    connect(collectionViewer: CollectionViewer): Observable<LabelDto[]> {
        return this.labelsSubject.asObservable();
    }

    disconnect(collectionViewer: CollectionViewer): void {
        this.labelsSubject.complete();
    }

    updateLabels(labels: LabelDto[]) {

        this.labelsSubject.next(labels);
    }
}
