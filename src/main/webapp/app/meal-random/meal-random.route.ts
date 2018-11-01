import { Route } from '@angular/router';

import { MealRandomComponent } from 'app/meal-random/meal-random.component';

export const mealRandomRoute: Route = {
    path: 'randomMeal',
    component: MealRandomComponent,
    data: {
        authorities: [],
        pageTitle: 'Random Meal'
    }
};
