import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Page} from "../ner-jobs/ner-jobs.service";
import {NerJobTextDto} from "./ner-job-text.dto";
import {NerJobDto} from "../ner-jobs/ner-job.dto";
import {NerJobTextAccessService} from "./ner-job-text-access.service";
import {Observable} from "rxjs";

@Component({
    selector: 'ner-job-texts-list',
    templateUrl: './ner-job-texts-list.component.html',
    styleUrls: ['./ner-job-texts-list.component.scss'],
    providers: [NerJobTextAccessService]
})
export class NerJobTextsListComponent implements OnInit, OnChanges {

    @Input() job: NerJobDto;

    displayedColumns = ["seqNo", "text", "delete"];

    pagedContent$: Observable<Page<NerJobTextDto>>;
    page: number = 1;

    constructor(private textAccessService: NerJobTextAccessService) {
    }

    ngOnInit() {
    }


    deleteText(textDto: NerJobTextDto){

    }

    ngOnChanges(changes: SimpleChanges): void {
        if (this.job  !== null){
            this.pagedContent$ = this.textAccessService.listText(this.job.id, this.page);
        }
    }
}
