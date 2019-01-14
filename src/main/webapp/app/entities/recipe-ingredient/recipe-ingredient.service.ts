import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRecipeIngredient } from 'app/shared/model/recipe-ingredient.model';

type EntityResponseType = HttpResponse<IRecipeIngredient>;
type EntityArrayResponseType = HttpResponse<IRecipeIngredient[]>;

@Injectable({ providedIn: 'root' })
export class RecipeIngredientService {
    public resourceUrl = SERVER_API_URL + 'api/recipe-ingredients';

    constructor(protected http: HttpClient) {}

    create(recipeIngredient: IRecipeIngredient): Observable<EntityResponseType> {
        return this.http.post<IRecipeIngredient>(this.resourceUrl, recipeIngredient, { observe: 'response' });
    }

    update(recipeIngredient: IRecipeIngredient): Observable<EntityResponseType> {
        return this.http.put<IRecipeIngredient>(this.resourceUrl, recipeIngredient, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IRecipeIngredient>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IRecipeIngredient[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
