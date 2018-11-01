import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMeal } from 'app/shared/model/meal.model';
import { MealService } from './meal.service';

@Component({
    selector: 'jhi-meal-update',
    templateUrl: './meal-update.component.html'
})
export class MealUpdateComponent implements OnInit {
    isSaving: boolean;

    constructor(private mealService: MealService, private activatedRoute: ActivatedRoute) {}

    private _meal: IMeal;

    get meal() {
        return this._meal;
    }

    set meal(meal: IMeal) {
        this._meal = meal;
    }

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

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMeal>>) {
        result.subscribe((res: HttpResponse<IMeal>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
