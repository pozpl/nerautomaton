import {Injectable} from "@angular/core";

import {AnnotatedResult} from "./annotated-result";

@Injectable()
export class TermsAnnotationsService {

    constructor() {
    }


    getHighlitning(terms: string[], annotationResults: AnnotatedResult[]): string[] {
        let annotations: string[] = terms.map(value => {
            return null;
        });


        for (const annotation of annotationResults) {
            for (let idx: number = annotation.begin; idx <= annotation.end; idx++) {
                annotations[idx] = annotation.annotation;
            }
        }

        return annotations;

    }


}
