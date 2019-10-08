import {Component, OnInit, Inject, Input, Output, EventEmitter} from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";
import {FormBuilder, FormGroup} from "@angular/forms";
import {NerJobTextEditDialog} from "./ner-job-text-edit-dialog.component";

@Component({
    selector: 'ner-job-text-edit',
    templateUrl: './ner-job-text-edit.component.html',
    styleUrls: ['./ner-job-text-edit.component.scss']
})
export class NerJobTextEditComponent implements OnInit {

    @Input() textDto: NerJobTextDto;
    @Output() savedText: EventEmitter<NerJobTextDto>;

    constructor(public dialog: MatDialog) {
    }


    editTextItem() {
        this.editText(this.textDto);
    }

    editText(textDto: NerJobTextDto){
        let dialogRef = this.dialog.open(NerJobTextEditDialog, {
            minHeight: 500,
            minWidth: 500,

            data: {
                text: textDto
            }
        });

        dialogRef.afterClosed().subscribe(
            data => console.log("Dialog output:", data)
        );
    }

    createTextItem(){
        this.editText(new NerJobTextDto());
    }


    ngOnInit() {
    }

}


