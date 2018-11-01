import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MealRandomComponent } from 'app/meal-random/meal-random.component';
import { mealRandomRoute } from 'app/meal-random/meal-random.route';
import { MealplannerSharedModule } from 'app/shared';

@NgModule({
    imports: [MealplannerSharedModule, RouterModule.forRoot([mealRandomRoute], { useHash: true })],
    declarations: [MealRandomComponent],
    entryComponents: [],
    providers: []
})
export class MealRandomModule {}
