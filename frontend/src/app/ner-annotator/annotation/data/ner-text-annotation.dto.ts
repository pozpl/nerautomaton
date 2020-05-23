import {TaggedTermDto} from "./tagged-term.dto";

export interface NerTextAnnotationDto {

    id:number;
    tokens: TaggedTermDto[];
    text: string;
}
