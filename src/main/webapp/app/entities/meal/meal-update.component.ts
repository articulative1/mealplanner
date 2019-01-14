import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMeal } from 'app/shared/model/meal.model';
import { MealService } from './meal.service';

@Component({
    selector: 'jhi-meal-update',
    templateUrl: './meal-update.component.html'
})
export class MealUpdateComponent implements OnInit {
    meal: IMeal;
    isSaving: boolean;

    constructor(protected mealService: MealService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ meal }) => {
            this.meal = meal;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.meal.id !== undefined) {
            this.subscribeToSaveResponse(this.mealService.update(this.meal));
        } else {
            this.subscribeToSaveResponse(this.mealService.create(this.meal));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeal>>) {
        result.subscribe((res: HttpResponse<IMeal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
