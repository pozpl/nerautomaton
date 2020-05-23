import {TaggedTermDto} from "./tagged-term.dto";

export interface NetTextAnnotationDto {

    id:number;
    tokens: TaggedTermDto[];
    text: string;
}
