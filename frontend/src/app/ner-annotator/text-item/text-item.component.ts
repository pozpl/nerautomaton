import {Component, HostListener, Injectable, OnInit} from '@angular/core';
import {TextItemDto} from "./text-item-dto";
import {PostDataSource} from "../../dashboard/dashboard.component";
import {DataSource} from "@angular/cdk/table";
import {DataService} from "../../data/data.service";
import {Observable, of} from "rxjs";
import {Post} from "../../Post";
import {ResultsDataService} from "./results-data.service";
import {AnnotatedResult} from "./annotated-result";
import {TextSelectEvent} from "./text-select.directive";

interface SelectionRectangle {
  left: number;
  top: number;
  width: number;
  height: number;
}

@Component({
  selector: 'app-text-item',
  templateUrl: './text-item.component.html',
  styleUrls: ['./text-item.component.scss']
})
export class TextItemComponent implements OnInit {


  itemDto: TextItemDto;

  tokens: string[];

  results: AnnotatedResult[];

  resultsDisplayedColumns = ['tokens', 'annotation', 'delete'];
  resultsDataSource: ResultsDataSource;

  annotationCandidateBeginIndex: number;
  annotationCandidate: AnnotationCandidate;
  selectedAnnotation: string;

  public hostRectangle: SelectionRectangle | null;
  private selectedText: string;

  constructor(private resultsDataService: ResultsDataService) {
    this.results = new Array<AnnotatedResult>();

    this.hostRectangle = null;
    this.selectedText = "";
  }


  ngOnInit() {
    this.itemDto = new TextItemDto("text>>term1>>term2>>term3>>term4", [
      "annotation1", "annotation2"
    ]);

    this.createTextFromTokens(this.itemDto.text);

    this.resultsDataSource = new ResultsDataSource(this.resultsDataService);

    this.resultsDataService.addResult(
      new AnnotatedResult(
        ['error', 'shmerror'],
        'annotation',
        1,
        3
      )
    );
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

  // Render the rectangles emitted by the  [textSelect] directive.
  public renderRectangles(event: TextSelectEvent): void {
    
    if (event.startElIdx >= 0 && event.endElIdx >= 0) {
      if (event.startElIdx < event.endElIdx) {
        this.selectStart(event.startElIdx);
        this.selectEnd(event.endElIdx);
      } else {
        this.selectStart(event.endElIdx);
        this.selectEnd(event.startElIdx);
      }

    }

    // If a new selection has been created, the viewport and host rectangles will
    // exist. Or, if a selection is being removed, the rectangles will be null.
    if (event.hostRectangle) {

      this.hostRectangle = event.hostRectangle;
      this.selectedText = event.text;

    } else {

      this.hostRectangle = null;
      this.selectedText = "";

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

    this.resultsDataSource = new ResultsDataSource(this.resultsDataService);
    this.annotationCandidate = null;
    this.selectedAnnotation = null;

    this.hideSelection()
  }

  selectAndApprove(annotation: string){
    this.selectAnnotation(annotation);
    this.approveAnnotation();
  }

  
  deleteResult(beginIdx, endIdx) {
    this.resultsDataService.deleteResult(beginIdx, endIdx);
    this.resultsDataSource = new ResultsDataSource(this.resultsDataService);
  }

  private createTextFromTokens(tokensSequence: String) {
    this.tokens = tokensSequence.split(">>");
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


class ResultsDataSource extends DataSource<any> {
  constructor(private dataService: ResultsDataService) {
    super();
  }

  connect(): Observable<AnnotatedResult[]> {
    return this.dataService.getResults();
  }

  disconnect() {
  }
}



