export interface Page<T> {
    content: Array<T>;
    pagable: any;
    totalElements: number;
}
