export const enum MeasurementType {
    TABLESPOON = 'TABLESPOON',
    TEASPOON = 'TEASPOON',
    CUP = 'CUP',
    OUNCE = 'OUNCE',
    POUND = 'POUND',
    OZ = 'OZ',
    FLOZ = 'FLOZ'
}

export interface IRecipeIngredient {
    id?: number;
    measurementType?: MeasurementType;
    measurementValue?: number;
    mealId?: number;
    ingredientId?: number;
}

export class RecipeIngredient implements IRecipeIngredient {
    constructor(
        public id?: number,
        public measurementType?: MeasurementType,
        public measurementValue?: number,
        public mealId?: number,
        public ingredientId?: number
    ) {}
}
