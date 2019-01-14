package com.mealplanner.service.mapper;

import com.mealplanner.domain.*;
import com.mealplanner.service.dto.RecipeIngredientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RecipeIngredient and its DTO RecipeIngredientDTO.
 */
@Mapper(componentModel = "spring", uses = {MealMapper.class, IngredientMapper.class})
public interface RecipeIngredientMapper extends EntityMapper<RecipeIngredientDTO, RecipeIngredient> {

    @Mapping(source = "meal.id", target = "mealId")
    @Mapping(source = "ingredient.id", target = "ingredientId")
    RecipeIngredientDTO toDto(RecipeIngredient recipeIngredient);

    @Mapping(source = "mealId", target = "meal")
    @Mapping(source = "ingredientId", target = "ingredient")
    RecipeIngredient toEntity(RecipeIngredientDTO recipeIngredientDTO);

    default RecipeIngredient fromId(Long id) {
        if (id == null) {
            return null;
        }
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(id);
        return recipeIngredient;
    }
}
