import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';

import { MealplannerMealModule } from './meal/meal.module';
import { MealplannerScheduleModule } from './schedule/schedule.module';

import { MealplannerRecipeIngredientModule } from './recipe-ingredient/recipe-ingredient.module';
import { MealplannerIngredientModule } from './ingredient/ingredient.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        MealplannerMealModule,
        MealplannerScheduleModule,
        MealplannerRecipeIngredientModule,
        MealplannerIngredientModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MealplannerEntityModule {}
