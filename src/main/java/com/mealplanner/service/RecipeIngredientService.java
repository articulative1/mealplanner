package com.mealplanner.service;

import com.mealplanner.domain.RecipeIngredient;
import com.mealplanner.repository.RecipeIngredientRepository;
import com.mealplanner.service.dto.RecipeIngredientDTO;
import com.mealplanner.service.mapper.RecipeIngredientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RecipeIngredient.
 */
@Service
@Transactional
public class RecipeIngredientService {

    private final Logger log = LoggerFactory.getLogger(RecipeIngredientService.class);

    private final RecipeIngredientRepository recipeIngredientRepository;

    private final RecipeIngredientMapper recipeIngredientMapper;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository, RecipeIngredientMapper recipeIngredientMapper) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeIngredientMapper = recipeIngredientMapper;
    }

    /**
     * Save a recipeIngredient.
     *
     * @param recipeIngredientDTO the entity to save
     * @return the persisted entity
     */
    public RecipeIngredientDTO save(RecipeIngredientDTO recipeIngredientDTO) {
        log.debug("Request to save RecipeIngredient : {}", recipeIngredientDTO);

        RecipeIngredient recipeIngredient = recipeIngredientMapper.toEntity(recipeIngredientDTO);
        recipeIngredient = recipeIngredientRepository.save(recipeIngredient);
        return recipeIngredientMapper.toDto(recipeIngredient);
    }

    /**
     * Get all the recipeIngredients.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RecipeIngredientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RecipeIngredients");
        return recipeIngredientRepository.findAll(pageable)
            .map(recipeIngredientMapper::toDto);
    }


    /**
     * Get one recipeIngredient by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RecipeIngredientDTO> findOne(Long id) {
        log.debug("Request to get RecipeIngredient : {}", id);
        return recipeIngredientRepository.findById(id)
            .map(recipeIngredientMapper::toDto);
    }

    /**
     * Delete the recipeIngredient by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RecipeIngredient : {}", id);
        recipeIngredientRepository.deleteById(id);
    }
}
