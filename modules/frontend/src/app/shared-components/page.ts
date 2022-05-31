export interface Page<T> {
    content?: Array<T>;
    curPage: number;
    size: number;
    totalPage: number;
}
