import {Component, HostListener, Injectable, OnInit} from '@angular/core';
import {TextItemDto} from "./text-item-dto";
import {ResultsDataService} from "./results-data.service";
import {AnnotatedResult} from "./annotated-result";
import {TextSelectEvent} from "./text-select.directive";
import {TermsAnnotationsService} from "./terms-annotations.service";

interface SelectionRectangle {
    left: number;
    top: number;
    width: number;
    height: number;
}

@Component({
    selector: 'app-text-item',
    templateUrl: './text-item.component.html',
    styleUrls: ['./text-item.component.scss'],
    providers: [ResultsDataService,TermsAnnotationsService]
})
export class TextItemComponent implements OnInit {


    itemDto: TextItemDto;

    tokens: string[];

    annotationCandidateBeginIndex: number;
    annotationCandidate: AnnotationCandidate;
    selectedAnnotation: string;

    public hostRectangle: SelectionRectangle | null;
    private selectedText: string;

    tokensAnnotations: string[];
    showOverlappingRegionsMessage: boolean = false;

    annotationsMap: Map<string, number>;

    constructor(public resultsDataService: ResultsDataService,
                private termsAnnotationsService: TermsAnnotationsService) {

        this.hostRectangle = null;
        this.selectedText = "";
        this.tokensAnnotations = [];
    }


    ngOnInit() {
        this.itemDto = new TextItemDto("text>>term1>>term2>>term3>>term4", [
            "annotation1", "annotation2", "annotation3", "annotation4"
        ]);

        this.createTextFromTokens(this.itemDto.text);

        this.annotationsMap = this.assignColoursIdxesToAnnotations(this.itemDto.annotations)

        this.onResultsChange();
    }

    onResultsChange() {
        this.resultsDataService.getResults().subscribe(value => {
            this.tokensAnnotations = this.termsAnnotationsService.getHighlitning(
                this.tokens,
                value);
        });
    }

    selectStart(index: number) {
        this.annotationCandidateBeginIndex = index;
        this.annotationCandidate = null;
        this.selectedAnnotation = null;
    }

    selectEnd(index: Number) {

        let beginIdx;
        let endIdx;
        if (index > this.annotationCandidateBeginIndex) {
            beginIdx = this.annotationCandidateBeginIndex;
            endIdx = index
        } else {
            beginIdx = index;
            endIdx = this.annotationCandidateBeginIndex
        }
        let annotationCandidateTerms = this.tokens.filter((token: String, tokIndex: Number, array) => {
            return (tokIndex >= beginIdx) && (tokIndex <= endIdx);
        });

        this.annotationCandidate = new AnnotationCandidate(annotationCandidateTerms, beginIdx, endIdx);
    }

    /**
     * Render the rectangles emitted by the  [textSelect] directive.
     * @param event
     */
    public renderRectangles(event: TextSelectEvent): void {

        // If a new selection has been created, the viewport and host rectangles will
        // exist. Or, if a selection is being removed, the rectangles will be null.
        if (event.hostRectangle) {

            this.hostRectangle = event.hostRectangle;
            this.selectedText = event.text;

            if (event.startElIdx >= 0 && event.endElIdx >= 0) {
                let startSel: number;
                let endSel: number;
                if (event.startElIdx < event.endElIdx) {
                    startSel = event.startElIdx;
                    endSel = event.endElIdx;
                } else {
                    startSel = event.endElIdx;
                    endSel = event.startElIdx;
                }

                if(this.checkThatRegionsCanBeAnnotated(startSel, endSel)) {
                    this.selectStart(startSel);
                    this.selectEnd(endSel);
                }else{
                    this.showOverlappingRegionsMessage = true;
                }

            }

        } else {

            this.hostRectangle = null;
            this.selectedText = "";
            this.showOverlappingRegionsMessage = false;

        }

    }

    /**
     * Hide selection and remove all controls
     */
    private hideSelection(): void {

        // Now that we've shared the text, let's clear the current selection.
        document.getSelection().removeAllRanges();
        // CAUTION: In modern browsers, the above call triggers a "selectionchange"
        // event, which implicitly calls our renderRectangles() callback. However,
        // in IE, the above call doesn't appear to trigger the "selectionchange"
        // event. As such, we need to remove the host rectangle explicitly.
        this.hostRectangle = null;
        this.selectedText = "";
        this.showOverlappingRegionsMessage = false; //returning to default state if we showed error about overlapping regions

    }


    selectAnnotation(annotation: string) {
        if (this.annotationCandidate != null) {
            this.selectedAnnotation = annotation;
        }

    }

    /**
     * Approve annotation candidate thus edding it into the list of annotations for given text
     */
    approveAnnotation() {
        this.resultsDataService.addResult(
            new AnnotatedResult(
                this.annotationCandidate.terms,
                this.selectedAnnotation,
                this.annotationCandidate.begin,
                this.annotationCandidate.end
            )
        );

        // this.resultsDataSource = new ResultsDataSource(this.resultsDataService);
        this.annotationCandidate = null;
        this.selectedAnnotation = null;

        this.hideSelection()
    }

    selectAndApprove(annotation: string) {
        this.selectAnnotation(annotation);
        this.approveAnnotation();
    }

    submitAndGoToNewTask(){

    }
    

    private createTextFromTokens(tokensSequence: String) {
        this.tokens = tokensSequence.split(">>");
        this.tokensAnnotations = this.tokens.map(value => null);
    }

    private checkThatRegionsCanBeAnnotated(begin: number,
                                           end: number): boolean {
        return this.tokensAnnotations.slice(begin, end).every(termAnnotation => {
            return termAnnotation == null;
        });
    }

    private assignColoursIdxesToAnnotations(annotaitons: string[]): Map<string, number>{
        let annotationsMap: Map<string, number> = new Map();
        annotaitons.forEach((annotation, index) => {
            annotationsMap.set(annotation, index + 1);
        });

        return annotationsMap;
    }

}

class AnnotationCandidate {
    private readonly _terms: string[];
    private readonly _begin: number;
    private readonly _end: number;


    constructor(terms: string[], begin: number, end: number) {
        this._terms = terms;
        this._begin = begin;
        this._end = end;
    }


    get terms(): string[] {
        return this._terms;
    }

    get begin(): number {
        return this._begin;
    }

    get end(): number {
        return this._end;
    }
}






