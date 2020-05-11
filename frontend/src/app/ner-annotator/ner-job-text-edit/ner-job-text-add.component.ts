import {Component, OnInit, Inject, Input, Output, EventEmitter} from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";
import {NerJobTextEditDialog} from "./ner-job-text-edit-dialog.component";
import {NerJobTextEditService} from "./ner-job-text-edit.service";
import {flatMap} from "rxjs/operators";
import {of} from "rxjs";
import {NerJobDto} from "../ner-jobs/ner-job.dto";
import {NerJobTextEditModalService} from "./ner-job-text-edit-modal.service";

@Component({
    selector: 'ner-job-text-edit',
    templateUrl: './ner-job-text-add.component.html',
    styleUrls: ['./ner-job-text-add.component.scss'],
    providers: [NerJobTextEditService]
})
export class NerJobTextAddComponent implements OnInit {

    @Input("job") jobDto: NerJobDto;
    @Output() savedText: EventEmitter<NerJobTextDto> = new EventEmitter<NerJobTextDto>();

    constructor(private nerJobTextEditModalService: NerJobTextEditModalService,
                private textService: NerJobTextEditService) {
    }

    ngOnInit() {
    }

    createTextItem() {
        this.nerJobTextEditModalService.openNewTextForJob(this.jobDto.id).pipe(
            flatMap(textDto => {
                if (textDto !== null) {
                    return this.textService.save(textDto);
                }
                return of(null);
            })
        )
            .subscribe(
                textDto => {
                    if (textDto !== null) {
                        this.savedText.emit(textDto);
                    }
                },
                cancel => console.log("closed with error")
            );
    }
}


