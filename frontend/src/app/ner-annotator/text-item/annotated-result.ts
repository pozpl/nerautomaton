export class AnnotatedResult {

  private readonly _tokens: string[];
  private readonly _annotation: string;
  private readonly _begin: number;
  private readonly _end: number;

  public constructor(terms: string[], annotation: string, begin: number, end: number) {
    this._tokens = terms;
    this._annotation = annotation;
    this._begin = begin;
    this._end = end;
  }


  get tokens(): string[] {
    return this._tokens;
  }

  get annotation(): string {
    return this._annotation;
  }

  get begin(): number {
    return this._begin;
  }

  get end(): number {
    return this._end;
  }
}
