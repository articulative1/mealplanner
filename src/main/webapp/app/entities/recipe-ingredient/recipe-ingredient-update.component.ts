import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRecipeIngredient } from 'app/shared/model/recipe-ingredient.model';
import { RecipeIngredientService } from './recipe-ingredient.service';
import { IMeal } from 'app/shared/model/meal.model';
import { MealService } from 'app/entities/meal';
import { IIngredient } from 'app/shared/model/ingredient.model';
import { IngredientService } from 'app/entities/ingredient';

@Component({
    selector: 'jhi-recipe-ingredient-update',
    templateUrl: './recipe-ingredient-update.component.html'
})
export class RecipeIngredientUpdateComponent implements OnInit {
    recipeIngredient: IRecipeIngredient;
    isSaving: boolean;

    meals: IMeal[];

    ingredients: IIngredient[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected recipeIngredientService: RecipeIngredientService,
        protected mealService: MealService,
        protected ingredientService: IngredientService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipeIngredient }) => {
            this.recipeIngredient = recipeIngredient;
        });
        this.mealService.query().subscribe(
            (res: HttpResponse<IMeal[]>) => {
                this.meals = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.ingredientService.query().subscribe(
            (res: HttpResponse<IIngredient[]>) => {
                this.ingredients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recipeIngredient.id !== undefined) {
            this.subscribeToSaveResponse(this.recipeIngredientService.update(this.recipeIngredient));
        } else {
            this.subscribeToSaveResponse(this.recipeIngredientService.create(this.recipeIngredient));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRecipeIngredient>>) {
        result.subscribe((res: HttpResponse<IRecipeIngredient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackMealById(index: number, item: IMeal) {
        return item.id;
    }

    trackIngredientById(index: number, item: IIngredient) {
        return item.id;
    }
}
