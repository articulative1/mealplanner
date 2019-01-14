package com.mealplanner.service.mapper;

import com.mealplanner.domain.*;
import com.mealplanner.service.dto.MealDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Meal and its DTO MealDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MealMapper extends EntityMapper<MealDTO, Meal> {


    @Mapping(target = "recipeIngredients", ignore = true)
    Meal toEntity(MealDTO mealDTO);

    default Meal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Meal meal = new Meal();
        meal.setId(id);
        return meal;
    }
}
