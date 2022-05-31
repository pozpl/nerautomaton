import {NerTextAnnotationDto} from "./ner-text-annotation.dto";

export interface NerTextAnnotationEditResultDto {
    status: boolean;
    editResult: NerTextAnnotationDto | null;
    error: string | null;
}
