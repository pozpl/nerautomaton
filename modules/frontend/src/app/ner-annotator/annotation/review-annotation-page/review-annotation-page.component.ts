import {Component, OnInit} from '@angular/core';
import {NerTextAnnotationDto} from "../data/ner-text-annotation.dto";
import {NerAnnotationDataService} from "../data/ner-annotation-data.service";
import {LabelDto} from "../../management/ner-jobs/label.dto";
import { Location } from '@angular/common';

@Component({
    selector: 'ner-review-annotation-page',
    templateUrl: './review-annotation-page.component.html',
    styleUrls: ['./review-annotation-page.component.scss']
})
export class ReviewAnnotationPageComponent implements OnInit {

    annotatedText: NerTextAnnotationDto;
    labels: LabelDto[];

    constructor(private annotationDataService: NerAnnotationDataService,
                private location: Location) {
    }


    ngOnInit() {

        const stateObject = this.location.getState() as {
            annotatedText: NerTextAnnotationDto,
            labels: LabelDto[]
        };

        this.annotatedText = stateObject.annotatedText;
        this.labels = stateObject.labels;
    }

    processFinishedAnnotation(processedText: NerTextAnnotationDto) {

        this.annotationDataService.saveAnnotation(processedText).subscribe(
            editResult => {
                this.goBackToTextsList();
            });

    }

    goBackToTextsList(){
        this.location.back();
    }

}
