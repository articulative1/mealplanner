import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISchedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { IMeal } from 'app/shared/model/meal.model';
import { MealService } from 'app/entities/meal';

@Component({
    selector: 'jhi-schedule-update',
    templateUrl: './schedule-update.component.html'
})
export class ScheduleUpdateComponent implements OnInit {
    isSaving: boolean;
    meals: IMeal[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private scheduleService: ScheduleService,
        private mealService: MealService,
        private activatedRoute: ActivatedRoute
    ) {}

    private _schedule: ISchedule;

    get schedule() {
        return this._schedule;
    }

    set schedule(schedule: ISchedule) {
        this._schedule = schedule;
    }

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ schedule }) => {
            this.schedule = schedule;
        });
        this.mealService.query().subscribe(
            (res: HttpResponse<IMeal[]>) => {
                this.meals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.schedule.id !== undefined) {
            this.subscribeToSaveResponse(this.scheduleService.update(this.schedule));
        } else {
            this.subscribeToSaveResponse(this.scheduleService.create(this.schedule));
        }
    }

    trackMealById(index: number, item: IMeal) {
        return item.id;
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>) {
        result.subscribe((res: HttpResponse<ISchedule>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
