import {Component, Inject, OnInit} from "@angular/core";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";

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
        this.data.text = this.form.value.text;
        this.dialogRef.close(this.data);
    }

    close(){
        this.dialogRef.close();
    }

    ngOnInit(): void {

        this.form = this.createFormGroup();
        this.form = this.fb.group({
            text: [this.text, []],
        });
    }

    createFormGroup() {
        return new FormGroup({
            text: new FormControl()
        });
    }
}
