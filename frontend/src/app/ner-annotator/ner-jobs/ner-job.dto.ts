export class NerJobDto {

    private _id: number;
    private _name: string;
    private _created: Date;


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get created(): Date {
        return this._created;
    }

    set created(value: Date) {
        this._created = value;
    }
}
