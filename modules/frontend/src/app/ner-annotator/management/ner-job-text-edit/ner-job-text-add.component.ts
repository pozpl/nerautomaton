import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {NerJobTextDto} from "../ner-job-texts-list/ner-job-text.dto";
import {NerJobTextEditService} from "./ner-job-text-edit.service";
import {flatMap} from "rxjs/operators";
import {of} from "rxjs";
import {NerJobDto} from "../ner-jobs/ner-job.dto";
import {NerJobTextEditModalService} from "./ner-job-text-edit-modal.service";

@Component({
    selector: 'ner-job-text-add',
    templateUrl: './ner-job-text-add.component.html',
    styleUrls: ['./ner-job-text-add.component.scss'],
    providers: [NerJobTextEditService]
})
export class NerJobTextAddComponent implements OnInit {

    @Input("job") jobDto: NerJobDto;
    @Output() savedText: EventEmitter<NerJobTextDto> = new EventEmitter<NerJobTextDto>();
    @Output() textsUploaded: EventEmitter<void> = new EventEmitter<void>();

    constructor(private nerJobTextEditModalService: NerJobTextEditModalService,
                private textService: NerJobTextEditService) {
    }

    ngOnInit() {
    }

    createTextItem() {
        this.nerJobTextEditModalService.openNewTextForJob(this.jobDto.id).pipe(
            flatMap(textDto => {
                if (textDto != null) {
                    return this.textService.save(textDto);
                }
                return of(null);
            })
        )
            .subscribe(textDtoSaveStatus => {
                    if (textDtoSaveStatus !== null) {
                        this.savedText.emit(textDtoSaveStatus.jobTextDto);
                    }
                },
                cancel => console.log("closed with error")
            );
    }

    onTextsUpload(status){
        if(status){
            this.textsUploaded.emit();
        }
    }
}


