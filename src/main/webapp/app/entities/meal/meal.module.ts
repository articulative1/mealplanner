import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MealplannerSharedModule } from 'app/shared';
import {
    MealComponent,
    MealDeleteDialogComponent,
    MealDeletePopupComponent,
    MealDetailComponent,
    mealPopupRoute,
    mealRoute,
    MealUpdateComponent
} from './';

const ENTITY_STATES = [...mealRoute, ...mealPopupRoute];

@NgModule({
    imports: [MealplannerSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [MealComponent, MealDetailComponent, MealUpdateComponent, MealDeleteDialogComponent, MealDeletePopupComponent],
    entryComponents: [MealComponent, MealUpdateComponent, MealDeleteDialogComponent, MealDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MealplannerMealModule {}
