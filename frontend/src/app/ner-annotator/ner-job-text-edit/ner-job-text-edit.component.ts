import {Component, OnInit, Inject} from '@angular/core';
import {MatDialog, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
    selector: 'ner-job-text-edit',
    templateUrl: './ner-job-text-edit.component.html',
    styleUrls: ['./ner-job-text-edit.component.scss']
})
export class NerJobTextEditComponent implements OnInit {

    constructor(public dialog: MatDialog) {
    }

    openDialog() {
        this.dialog.open(NerJobTextEditDialog, {
            data: {
                animal: 'panda'
            }
        });
    }

    ngOnInit() {
    }

}

export interface DialogData {
    animal: 'panda' | 'unicorn' | 'lion';
}

@Component({
    selector: 'ner-job-edit-dialog',
    templateUrl: 'ner-job-edit-dialog.component.html',
})
export class NerJobTextEditDialog {
    constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData) {
    }
}

// @Component({
//     selector: 'dialog-data-example-dialog',
//     templateUrl: 'dialog-data-example-dialog.html',
// })
// export class DialogDataExampleDialog {
//     constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData) {}
// }
