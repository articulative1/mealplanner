import { IRecipeIngredient } from 'app/shared/model//recipe-ingredient.model';

export interface IMeal {
    id?: number;
    name?: string;
    recipe?: string;
    recipeIngredients?: IRecipeIngredient[];
}

export class Meal implements IMeal {
    constructor(public id?: number, public name?: string, public recipe?: string, public recipeIngredients?: IRecipeIngredient[]) {}
}
