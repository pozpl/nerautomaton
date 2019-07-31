import {Component, OnInit} from '@angular/core';
import {Page} from "../ner-jobs/ner-jobs.service";
import {NerJobTextDto} from "./ner-job-text.dto";

@Component({
    selector: 'app-ner-job-texts-list',
    templateUrl: './ner-job-texts-list.component.html',
    styleUrls: ['./ner-job-texts-list.component.scss']
})
export class NerJobTextsListComponent implements OnInit {

    displayedColumns = ["seqNo", "text", "delete"];

    pagedContent: Page<NerJobTextDto>;

    constructor() {
    }

    ngOnInit() {
    }


    deleteText(textDto: NerJobTextDto){

    }
}
