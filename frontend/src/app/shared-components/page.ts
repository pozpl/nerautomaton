export interface Page<T> {
    content: Array<T> | null;
    curPage: number;
    size: number;
    totalPage: number;
}
