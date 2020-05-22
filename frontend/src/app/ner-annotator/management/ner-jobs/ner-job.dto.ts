export class NerJobDto {

    public id: number;
    public name: string;
    public created: Date;

    public labels: LabelDto[]
}


export class LabelDto {
    public id: number;
    public name: string;
    public description: string;
}
