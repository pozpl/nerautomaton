import {Injectable} from "@angular/core";

import {AnnotatedResult} from "./annotated-result";
import {TaggedTermDto} from "../data/tagged-term.dto";

@Injectable()
export class TermsAnnotationsService {

    constructor() {
    }


    getHighlitning(terms: TaggedTermDto[], annotationResults: AnnotatedResult[]): TaggedTermDto[] {

        for (const annotationResult of annotationResults) {
            for (let termIdx: number = annotationResult.begin; termIdx <= annotationResult.end; termIdx++) {
                terms[termIdx].label = annotationResult.annotation.name;
            }
        }

        return terms;

    }


}
