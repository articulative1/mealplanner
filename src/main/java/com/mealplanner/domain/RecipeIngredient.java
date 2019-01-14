package com.mealplanner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.mealplanner.domain.enumeration.MeasurementType;

/**
 * A RecipeIngredient.
 */
@Entity
@Table(name = "recipe_ingredient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RecipeIngredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_type")
    private MeasurementType measurementType;

    @Column(name = "measurement_value")
    private Double measurementValue;

    @ManyToOne
    @JsonIgnoreProperties("recipeIngredients")
    private Meal meal;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Ingredient ingredient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public RecipeIngredient measurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
        return this;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public Double getMeasurementValue() {
        return measurementValue;
    }

    public RecipeIngredient measurementValue(Double measurementValue) {
        this.measurementValue = measurementValue;
        return this;
    }

    public void setMeasurementValue(Double measurementValue) {
        this.measurementValue = measurementValue;
    }

    public Meal getMeal() {
        return meal;
    }

    public RecipeIngredient meal(Meal meal) {
        this.meal = meal;
        return this;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public RecipeIngredient ingredient(Ingredient ingredient) {
        this.ingredient = ingredient;
        return this;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeIngredient recipeIngredient = (RecipeIngredient) o;
        if (recipeIngredient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipeIngredient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
            "id=" + getId() +
            ", measurementType='" + getMeasurementType() + "'" +
            ", measurementValue=" + getMeasurementValue() +
            "}";
    }
}
