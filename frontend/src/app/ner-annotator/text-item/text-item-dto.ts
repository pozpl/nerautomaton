export class TextItemDto{

  private _text: string;
  private _annotations: Array<String>;


  constructor(text: string, annotations: Array<String>) {
    this._text = text;
    this._annotations = annotations;
  }


  get text(): string {
    return this._text;
  }

  get annotations(): Array<String> {
    return this._annotations;
  }
}
