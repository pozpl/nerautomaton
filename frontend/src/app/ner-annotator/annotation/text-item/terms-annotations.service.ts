import {Injectable} from "@angular/core";

import {AnnotatedResult} from "./annotated-result";
import {TaggedTermDto} from "../data/tagged-term.dto";

@Injectable()
export class TermsAnnotationsService {

    constructor() {
    }


    getHighlitning(terms: TaggedTermDto[], annotationResults: AnnotatedResult[]): string[] {
        let annotations: string[] = terms.map(value => {
            return null;
        });


        for (const annotation of annotationResults) {
            for (let idx: number = annotation.begin; idx <= annotation.end; idx++) {
                annotations[idx] = annotation.annotation.name;
            }
        }

        return annotations;

    }


}
