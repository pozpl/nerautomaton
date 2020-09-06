export interface TaggedTermDto {
    token: string;
    label?: string | null;
    position?: 'BEGIN' | 'IN' | 'LAST' | 'UNIT' | 'OUT';
    specialTextMark?: 'SENTENCE_END';
}
