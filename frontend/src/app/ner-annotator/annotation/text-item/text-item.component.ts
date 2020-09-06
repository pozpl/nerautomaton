import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {ResultsDataService} from "./results-data.service";
import {AnnotatedResult} from "./annotated-result";
import {TextSelectEvent} from "./text-select.directive";
import {TermsAnnotationsService} from "./terms-annotations.service";
import {NerTextAnnotationDto} from "../data/ner-text-annotation.dto";
import {LabelDto} from "../../management/ner-jobs/label.dto";
import {TaggedTermDto} from "../data/tagged-term.dto";
import {Subject} from "rxjs";
import {takeUntil} from "rxjs/operators";
import {SelectionRectangle} from "./selection-rectangle";
import {AnnotationCandidate} from "./annotation-candidate";




@Component({
    selector: 'app-text-item',
    templateUrl: './text-item.component.html',
    styleUrls: ['./text-item.component.scss'],
    providers: [ResultsDataService, TermsAnnotationsService]
})
export class TextItemComponent implements OnInit, OnChanges {

    private unsubscribe: Subject<void> = new Subject<void>();

    @Input() nerTextAnnotationDto: NerTextAnnotationDto;
    @Input() labels: LabelDto[];

    @Output() finishedAnnotation = new EventEmitter<NerTextAnnotationDto>();
    @Output() onReturn = new EventEmitter<boolean>();

    annotationCandidateBeginIndex: number;
    annotationCandidate: AnnotationCandidate | null;
    selectedAnnotation: LabelDto | null;

    public hostRectangle: SelectionRectangle | null;
    private selectedText: string;

    showOverlappingRegionsMessage: boolean = false;

    annotationsMap: Map<string, number>;

    constructor(public resultsDataService: ResultsDataService,
                private termsAnnotationsService: TermsAnnotationsService) {

        this.hostRectangle = null;
        this.selectedText = "";
    }


    ngOnInit() {
        this.annotationsMap = this.assignColoursIdxesToAnnotations(this.labels);
        this.resultsDataService.initResults(this.termsAnnotationsService.getAnnotationResultsFromTerms(this.nerTextAnnotationDto.tokens));
        this.onResultsChange();
    }

    ngOnChanges(changes: SimpleChanges): void {
        if (changes.nerTextAnnotationDto){
            this.unsubscribe.next();//unsubscribe all previous subscriptions we are processing new item
            this.resultsDataService.initResults(this.termsAnnotationsService.getAnnotationResultsFromTerms(this.nerTextAnnotationDto.tokens));
            this.onResultsChange();
        }
    }


    private onResultsChange() {
        this.resultsDataService.getResults()
            .pipe(takeUntil(this.unsubscribe))
            .subscribe(value => {
                this.nerTextAnnotationDto.tokens = this.termsAnnotationsService.assignAnnotationResultsToTerms(
                    this.nerTextAnnotationDto.tokens,
                    value);
            });
    }

    private selectStart(index: number) {
        this.annotationCandidateBeginIndex = index;
        this.annotationCandidate = null;
        this.selectedAnnotation = null;
    }

    private selectEnd(index: number) {

        let beginIdx;
        let endIdx;
        if (index > this.annotationCandidateBeginIndex) {
            beginIdx = this.annotationCandidateBeginIndex;
            endIdx = index
        } else {
            beginIdx = index;
            endIdx = this.annotationCandidateBeginIndex
        }
        let annotationCandidateTerms = this.nerTextAnnotationDto.tokens.filter((token: TaggedTermDto, tokIndex: Number, array) => {
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

            if (event.startElIdx >= 0 && event.endElIdx >= 0) {
                this.hostRectangle = event.hostRectangle;
                this.selectedText = event.text;

                let startSel: number;
                let endSel: number;
                if (event.startElIdx < event.endElIdx) {
                    startSel = event.startElIdx;
                    endSel = event.endElIdx;
                } else {
                    startSel = event.endElIdx;
                    endSel = event.startElIdx;
                }

                if (this.checkThatRegionsCanBeAnnotated(startSel, endSel)) {
                    this.selectStart(startSel);
                    this.selectEnd(endSel);
                } else {
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
        const selection = document.getSelection();
        if(selection) {
            selection.removeAllRanges();
            // CAUTION: In modern browsers, the above call triggers a "selectionchange"
            // event, which implicitly calls our renderRectangles() callback. However,
            // in IE, the above call doesn't appear to trigger the "selectionchange"
            // event. As such, we need to remove the host rectangle explicitly.
            this.hostRectangle = null;
            this.selectedText = "";
            this.showOverlappingRegionsMessage = false; //returning to default state if we showed error about overlapping regions
        }
    }


    selectAnnotation(annotation: LabelDto) {
        if (this.annotationCandidate != null) {
            this.selectedAnnotation = annotation;
        }

    }

    /**
     * Approve annotation candidate thus adding it into the list of annotations for given text
     */
    approveAnnotation() {
        if(this.annotationCandidate && this.selectedAnnotation) {
            this.resultsDataService.addResult(
                new AnnotatedResult(
                    this.annotationCandidate.terms,
                    this.selectedAnnotation.name,
                    this.annotationCandidate.begin,
                    this.annotationCandidate.end
                )
            );

            this.annotationCandidate = null;
            this.selectedAnnotation = null;
        }

        this.hideSelection()
    }

    selectAndApprove(annotation: LabelDto) {
        this.selectAnnotation(annotation);
        this.approveAnnotation();
    }

    submitAndGoToNewTask() {
        this.finishedAnnotation.emit(this.nerTextAnnotationDto);
    }

    submitReturnToPreviousView() {
        this.onReturn.emit(true);
    }


    private checkThatRegionsCanBeAnnotated(begin: number,
                                           end: number): boolean {
        return this.nerTextAnnotationDto.tokens.slice(begin, end).every(token => {
            return token.label === null; //termAnnotation == null;
        });
    }

    private assignColoursIdxesToAnnotations(labelDtos: LabelDto[]): Map<string, number> {
        let annotationsMap: Map<string, number> = new Map();
        labelDtos.forEach((annotation, index) => {
            annotationsMap.set(annotation.name, index + 1);
        });

        return annotationsMap;
    }
}







