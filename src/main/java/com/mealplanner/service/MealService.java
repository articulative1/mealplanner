package com.mealplanner.service;

import com.mealplanner.domain.Meal;
import com.mealplanner.repository.MealRepository;
import com.mealplanner.service.dto.MealDTO;
import com.mealplanner.service.mapper.MealMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Meal.
 */
@Service
@Transactional
public class MealService {

    private final Logger log = LoggerFactory.getLogger(MealService.class);

    private final MealRepository mealRepository;

    private final MealMapper mealMapper;

    public MealService(MealRepository mealRepository, MealMapper mealMapper) {
        this.mealRepository = mealRepository;
        this.mealMapper = mealMapper;
    }

    /**
     * Save a meal.
     *
     * @param mealDTO the entity to save
     * @return the persisted entity
     */
    public MealDTO save(MealDTO mealDTO) {
        log.debug("Request to save Meal : {}", mealDTO);

        Meal meal = mealMapper.toEntity(mealDTO);
        meal = mealRepository.save(meal);
        return mealMapper.toDto(meal);
    }

    /**
     * Get all the meals.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MealDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Meals");
        return mealRepository.findAll(pageable)
            .map(mealMapper::toDto);
    }


    /**
     * Get one meal by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MealDTO> findOne(Long id) {
        log.debug("Request to get Meal : {}", id);
        return mealRepository.findById(id)
            .map(mealMapper::toDto);
    }

    /**
     * Delete the meal by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Meal : {}", id);
        mealRepository.deleteById(id);
    }
}
