package com.mealplanner.service;

import com.mealplanner.service.dto.RecipeIngredientDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RecipeIngredient.
 */
public interface RecipeIngredientService {

    /**
     * Save a recipeIngredient.
     *
     * @param recipeIngredientDTO the entity to save
     * @return the persisted entity
     */
    RecipeIngredientDTO save(RecipeIngredientDTO recipeIngredientDTO);

    /**
     * Get all the recipeIngredients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecipeIngredientDTO> findAll(Pageable pageable);


    /**
     * Get the "id" recipeIngredient.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RecipeIngredientDTO> findOne(Long id);

    /**
     * Delete the "id" recipeIngredient.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
