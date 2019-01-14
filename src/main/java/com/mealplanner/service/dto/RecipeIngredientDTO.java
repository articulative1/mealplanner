package com.mealplanner.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.mealplanner.domain.enumeration.MeasurementType;

/**
 * A DTO for the RecipeIngredient entity.
 */
public class RecipeIngredientDTO implements Serializable {

    private Long id;

    private MeasurementType measurementType;

    private Double measurementValue;

    private Long mealId;

    private Long ingredientId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public Double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(Double measurementValue) {
        this.measurementValue = measurementValue;
    }

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeIngredientDTO recipeIngredientDTO = (RecipeIngredientDTO) o;
        if (recipeIngredientDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeIngredientDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeIngredientDTO{" +
            "id=" + getId() +
            ", measurementType='" + getMeasurementType() + "'" +
            ", measurementValue=" + getMeasurementValue() +
            ", meal=" + getMealId() +
            ", ingredient=" + getIngredientId() +
            "}";
    }
}
