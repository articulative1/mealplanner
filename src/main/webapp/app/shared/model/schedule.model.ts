import { Moment } from 'moment';

export interface ISchedule {
    id?: number;
    date?: Moment;
    completed?: boolean;
    mealId?: number;
}

export class Schedule implements ISchedule {
    constructor(public id?: number, public date?: Moment, public completed?: boolean, public mealId?: number) {
        this.completed = this.completed || false;
    }
}
