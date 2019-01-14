import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MealplannerSharedModule } from 'app/shared';
import {
    RecipeIngredientComponent,
    RecipeIngredientDetailComponent,
    RecipeIngredientUpdateComponent,
    RecipeIngredientDeletePopupComponent,
    RecipeIngredientDeleteDialogComponent,
    recipeIngredientRoute,
    recipeIngredientPopupRoute
} from './';

const ENTITY_STATES = [...recipeIngredientRoute, ...recipeIngredientPopupRoute];

@NgModule({
    imports: [MealplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RecipeIngredientComponent,
        RecipeIngredientDetailComponent,
        RecipeIngredientUpdateComponent,
        RecipeIngredientDeleteDialogComponent,
        RecipeIngredientDeletePopupComponent
    ],
    entryComponents: [
        RecipeIngredientComponent,
        RecipeIngredientUpdateComponent,
        RecipeIngredientDeleteDialogComponent,
        RecipeIngredientDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MealplannerRecipeIngredientModule {}
