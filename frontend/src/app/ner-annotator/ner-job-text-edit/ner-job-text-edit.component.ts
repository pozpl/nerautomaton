import {Component, OnInit, Inject, Input, Output, EventEmitter} from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";
import {NerJobTextEditDialog} from "./ner-job-text-edit-dialog.component";
import {NerJobTextEditService} from "./ner-job-text-edit.service";
import {flatMap} from "rxjs/operators";
import {of} from "rxjs";
import {NerJobDto} from "../ner-jobs/ner-job.dto";

@Component({
    selector: 'ner-job-text-edit',
    templateUrl: './ner-job-text-edit.component.html',
    styleUrls: ['./ner-job-text-edit.component.scss'],
    providers: [NerJobTextEditService]
})
export class NerJobTextEditComponent implements OnInit {

    @Input("textDto") textDto: NerJobTextDto;
    @Input("job") jobDto: NerJobDto;
    @Output() savedText: EventEmitter<NerJobTextDto>;

    constructor(public dialog: MatDialog,
                private textService: NerJobTextEditService) {
    }


    editTextItem() {
        this.editText(this.textDto);
    }

    editText(textDto: NerJobTextDto) {
        let dialogRef = this.dialog.open(NerJobTextEditDialog, {
            minHeight: 500,
            minWidth: 500,

            data: textDto
        });

        dialogRef.afterClosed().pipe(
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
                        this.textDto = textDto.jobTextDto;
                    }
                },
                cancel => console.log("closed with error")
            );
    }

    createTextItem() {
        if (this.jobDto !== null && this.jobDto.id) {
            const textToAdd = new NerJobTextDto();
            textToAdd.jobId = this.jobDto.id;
            this.editText(textToAdd);
        }
    }


    ngOnInit() {
    }

}


