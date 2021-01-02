export interface TaggedTermDto {
    token: string;
    label?: string | null;
    position?: null | 'BEGIN' | 'IN' | 'LAST' | 'UNIT' | 'OUT';
    specialTextMark?: 'SENTENCE_END';

    annotationBeginIdx?: number | null;//start index for tagged term
    annotationEndIdx?: number | null;//end index for tagged term
}
