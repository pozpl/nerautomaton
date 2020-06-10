import {TaggedTermDto} from "../data/tagged-term.dto";

export class AnnotationCandidate {
    private readonly _terms: TaggedTermDto[];
    private readonly _begin: number;
    private readonly _end: number;


    constructor(terms: TaggedTermDto[], begin: number, end: number) {
        this._terms = terms;
        this._begin = begin;
        this._end = end;
    }


    get terms(): TaggedTermDto[] {
        return this._terms;
    }

    get begin(): number {
        return this._begin;
    }

    get end(): number {
        return this._end;
    }
}
