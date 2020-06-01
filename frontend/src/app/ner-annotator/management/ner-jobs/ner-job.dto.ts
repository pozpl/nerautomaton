import {LabelDto} from "./label.dto";

export class NerJobDto {

    public id: number;
    public name: string;
    public created: Date;

    public labels?: LabelDto[]
}
