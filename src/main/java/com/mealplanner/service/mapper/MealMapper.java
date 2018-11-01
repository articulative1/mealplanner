package com.mealplanner.service.mapper;

import com.mealplanner.domain.Meal;
import com.mealplanner.service.dto.MealDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity Meal and its DTO MealDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MealMapper extends EntityMapper<MealDTO, Meal> {


    default Meal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Meal meal = new Meal();
        meal.setId(id);
        return meal;
    }
}
