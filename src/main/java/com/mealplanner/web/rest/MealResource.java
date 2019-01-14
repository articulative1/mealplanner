package com.mealplanner.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mealplanner.service.MealService;
import com.mealplanner.web.rest.errors.BadRequestAlertException;
import com.mealplanner.web.rest.util.HeaderUtil;
import com.mealplanner.web.rest.util.PaginationUtil;
import com.mealplanner.service.dto.MealDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Meal.
 */
@RestController
@RequestMapping("/api")
public class MealResource {

    private final Logger log = LoggerFactory.getLogger(MealResource.class);

    private static final String ENTITY_NAME = "meal";

    private final MealService mealService;

    public MealResource(MealService mealService) {
        this.mealService = mealService;
    }

    /**
     * POST  /meals : Create a new meal.
     *
     * @param mealDTO the mealDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mealDTO, or with status 400 (Bad Request) if the meal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/meals")
    @Timed
    public ResponseEntity<MealDTO> createMeal(@RequestBody MealDTO mealDTO) throws URISyntaxException {
        log.debug("REST request to save Meal : {}", mealDTO);
        if (mealDTO.getId() != null) {
            throw new BadRequestAlertException("A new meal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MealDTO result = mealService.save(mealDTO);
        return ResponseEntity.created(new URI("/api/meals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /meals : Updates an existing meal.
     *
     * @param mealDTO the mealDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mealDTO,
     * or with status 400 (Bad Request) if the mealDTO is not valid,
     * or with status 500 (Internal Server Error) if the mealDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/meals")
    @Timed
    public ResponseEntity<MealDTO> updateMeal(@RequestBody MealDTO mealDTO) throws URISyntaxException {
        log.debug("REST request to update Meal : {}", mealDTO);
        if (mealDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MealDTO result = mealService.save(mealDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mealDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /meals : get all the meals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of meals in body
     */
    @GetMapping("/meals")
    @Timed
    public ResponseEntity<List<MealDTO>> getAllMeals(Pageable pageable) {
        log.debug("REST request to get a page of Meals");
        Page<MealDTO> page = mealService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/meals");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /meals/:id : get the "id" meal.
     *
     * @param id the id of the mealDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mealDTO, or with status 404 (Not Found)
     */
    @GetMapping("/meals/{id}")
    @Timed
    public ResponseEntity<MealDTO> getMeal(@PathVariable Long id) {
        log.debug("REST request to get Meal : {}", id);
        Optional<MealDTO> mealDTO = mealService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mealDTO);
    }

    /**
     * DELETE  /meals/:id : delete the "id" meal.
     *
     * @param id the id of the mealDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/meals/{id}")
    @Timed
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        log.debug("REST request to delete Meal : {}", id);
        mealService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
