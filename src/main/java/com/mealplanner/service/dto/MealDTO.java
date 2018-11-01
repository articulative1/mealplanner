package com.mealplanner.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Meal entity.
 */
public class MealDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String recipe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MealDTO mealDTO = (MealDTO) o;
        if (mealDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mealDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MealDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", recipe='" + getRecipe() + "'" +
            "}";
    }
}
