import {Component, OnInit, Inject, Input, Output, EventEmitter} from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";
import {FormBuilder, FormGroup} from "@angular/forms";

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


@Component({
    selector: 'ner-job-edit-dialog',
    templateUrl: 'ner-job-edit-dialog.component.html',
    styleUrls: ['./ner-job-text-edit-dialog.component.scss']
})
export class NerJobTextEditDialog implements OnInit{

    form: FormGroup;
    text:string;


    constructor(@Inject(MAT_DIALOG_DATA) public data: NerJobTextDto,
                private dialogRef: MatDialogRef<NerJobTextEditDialog>,
                private fb: FormBuilder) {
        this.text = data.text;
    }

    save(){
        this.dialogRef.close(this.form.value);
    }

    close(){
        this.dialogRef.close();
    }

    ngOnInit(): void {

        this.form = this.fb.group({
            text: [this.text, []],
        });
    }
}
