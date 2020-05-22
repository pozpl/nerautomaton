export interface TaggedTermDto {
    token: string;
    label: string;
    position: 'BEGIN' | 'IN' | 'LAST' | 'UNIT' | 'OUT';
    specialTextMark: 'SENTENCE_END';
}
