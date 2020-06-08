import {Injectable} from "@angular/core";

import {AnnotatedResult} from "./annotated-result";
import {TaggedTermDto} from "../data/tagged-term.dto";

@Injectable()
export class TermsAnnotationsService {

    constructor() {
    }


    assignAnnotationResultsToTerms(terms: TaggedTermDto[], annotationResults: AnnotatedResult[]): TaggedTermDto[] {

        terms.map(term => term.label = null);//delete all labels

        //assign labels from annotation results
        for (const annotationResult of annotationResults) {
            for (let termIdx: number = annotationResult.begin; termIdx <= annotationResult.end; termIdx++) {
                terms[termIdx].label = annotationResult.annotation;
            }
        }

        return terms;

    }

    getAnnotationResultsFromTerms(terms: TaggedTermDto[]): AnnotatedResult[]{
        const annotatedResults:AnnotatedResult[] = [];

        let currentLabel:string|null = null;
        let begin: number|null = null;
        let currentTerms: TaggedTermDto[] = [];
        for (let i = 0; i < terms.length; i++){
            const term = terms[i];
            if (term.label != null){
                currentLabel = term.label;
                if (term.position === 'BEGIN'){
                    currentTerms = [];
                    currentTerms.push(term);
                    begin = i;
                } else if (term.position === 'IN'){
                    currentTerms.push(term);
                } else if(term.position === 'LAST'){

                    annotatedResults.push(new AnnotatedResult(
                        currentTerms,
                        currentLabel,
                        begin, i
                    ));

                    currentTerms = [];
                    currentLabel = null;
                    begin = null;
                }else if(term.position === 'UNIT'){
                    currentTerms = [];
                    currentTerms.push(term);
                    annotatedResults.push(new AnnotatedResult(
                        currentTerms,
                        currentLabel,
                        i, i
                    ));
                    currentTerms = [];
                    currentLabel = null;
                    begin = null;
                }
            }

        }

        return annotatedResults;
    }


}
