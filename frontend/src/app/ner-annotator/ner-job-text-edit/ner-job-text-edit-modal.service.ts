import { Injectable } from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";
import {Observable} from "rxjs";
import {NerJobTextEditDialog} from "./ner-job-text-edit-dialog.component";

@Injectable({
  providedIn: 'root'
})
export class NerJobTextEditModalService {

  constructor(public dialog: MatDialog) { }

  openForEdit(nerJobTextDto: NerJobTextDto): Observable<NerJobTextDto> {
      let dialogRef = this.dialog.open(NerJobTextEditDialog, {
          minHeight: 500,
          minWidth: 500,

          data: nerJobTextDto
      });

      return dialogRef.afterClosed();
  }

  openNewTextForJob(jobId) : Observable<NerJobTextDto>{
      const textToAdd = new NerJobTextDto();
      textToAdd.jobId = jobId;

      return this.openForEdit(textToAdd);
  }

}
