import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { RecipeIngredient } from 'app/shared/model/recipe-ingredient.model';
import { RecipeIngredientService } from './recipe-ingredient.service';
import { RecipeIngredientComponent } from './recipe-ingredient.component';
import { RecipeIngredientDetailComponent } from './recipe-ingredient-detail.component';
import { RecipeIngredientUpdateComponent } from './recipe-ingredient-update.component';
import { RecipeIngredientDeletePopupComponent } from './recipe-ingredient-delete-dialog.component';
import { IRecipeIngredient } from 'app/shared/model/recipe-ingredient.model';

@Injectable({ providedIn: 'root' })
export class RecipeIngredientResolve implements Resolve<IRecipeIngredient> {
    constructor(private service: RecipeIngredientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<RecipeIngredient> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<RecipeIngredient>) => response.ok),
                map((recipeIngredient: HttpResponse<RecipeIngredient>) => recipeIngredient.body)
            );
        }
        return of(new RecipeIngredient());
    }
}

export const recipeIngredientRoute: Routes = [
    {
        path: 'recipe-ingredient',
        component: RecipeIngredientComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeIngredients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe-ingredient/:id/view',
        component: RecipeIngredientDetailComponent,
        resolve: {
            recipeIngredient: RecipeIngredientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeIngredients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe-ingredient/new',
        component: RecipeIngredientUpdateComponent,
        resolve: {
            recipeIngredient: RecipeIngredientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeIngredients'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipe-ingredient/:id/edit',
        component: RecipeIngredientUpdateComponent,
        resolve: {
            recipeIngredient: RecipeIngredientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeIngredients'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipeIngredientPopupRoute: Routes = [
    {
        path: 'recipe-ingredient/:id/delete',
        component: RecipeIngredientDeletePopupComponent,
        resolve: {
            recipeIngredient: RecipeIngredientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RecipeIngredients'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
