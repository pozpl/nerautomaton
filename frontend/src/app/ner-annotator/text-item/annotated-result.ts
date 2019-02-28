export class AnnotatedResult {

  private readonly _tokens: String[];
  private readonly _annotation: String;
  private readonly _begin: Number;
  private readonly _end: Number;

  public constructor(terms: String[], annotation: String, begin: Number, end: Number) {
    this._tokens = terms;
    this._annotation = annotation;
    this._begin = begin;
    this._end = end;
  }


  get tokens(): String[] {
    return this._tokens;
  }

  get annotation(): String {
    return this._annotation;
  }

  get begin(): Number {
    return this._begin;
  }

  get end(): Number {
    return this._end;
  }
}
