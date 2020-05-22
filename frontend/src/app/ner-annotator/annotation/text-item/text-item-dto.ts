export class TextItemDto{

  private _text: string;
  private _annotations: Array<string>;


  constructor(text: string, annotations: Array<string>) {
    this._text = text;
    this._annotations = annotations;
  }


  get text(): string {
    return this._text;
  }

  get annotations(): Array<string> {
    return this._annotations;
  }
}
