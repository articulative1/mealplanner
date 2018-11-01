export interface IMeal {
    id?: number;
    name?: string;
    recipe?: string;
}

export class Meal implements IMeal {
    constructor(public id?: number, public name?: string, public recipe?: string) {}
}
