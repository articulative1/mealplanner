import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRecipeIngredient } from 'app/shared/model/recipe-ingredient.model';

@Component({
    selector: 'jhi-recipe-ingredient-detail',
    templateUrl: './recipe-ingredient-detail.component.html'
})
export class RecipeIngredientDetailComponent implements OnInit {
    recipeIngredient: IRecipeIngredient;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ recipeIngredient }) => {
            this.recipeIngredient = recipeIngredient;
        });
    }

    previousState() {
        window.history.back();
    }
}
