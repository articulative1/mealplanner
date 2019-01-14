package com.mealplanner.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mealplanner.service.RecipeIngredientService;
import com.mealplanner.web.rest.errors.BadRequestAlertException;
import com.mealplanner.web.rest.util.HeaderUtil;
import com.mealplanner.web.rest.util.PaginationUtil;
import com.mealplanner.service.dto.RecipeIngredientDTO;
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
 * REST controller for managing RecipeIngredient.
 */
@RestController
@RequestMapping("/api")
public class RecipeIngredientResource {

    private final Logger log = LoggerFactory.getLogger(RecipeIngredientResource.class);

    private static final String ENTITY_NAME = "recipeIngredient";

    private final RecipeIngredientService recipeIngredientService;

    public RecipeIngredientResource(RecipeIngredientService recipeIngredientService) {
        this.recipeIngredientService = recipeIngredientService;
    }

    /**
     * POST  /recipe-ingredients : Create a new recipeIngredient.
     *
     * @param recipeIngredientDTO the recipeIngredientDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recipeIngredientDTO, or with status 400 (Bad Request) if the recipeIngredient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recipe-ingredients")
    @Timed
    public ResponseEntity<RecipeIngredientDTO> createRecipeIngredient(@RequestBody RecipeIngredientDTO recipeIngredientDTO) throws URISyntaxException {
        log.debug("REST request to save RecipeIngredient : {}", recipeIngredientDTO);
        if (recipeIngredientDTO.getId() != null) {
            throw new BadRequestAlertException("A new recipeIngredient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecipeIngredientDTO result = recipeIngredientService.save(recipeIngredientDTO);
        return ResponseEntity.created(new URI("/api/recipe-ingredients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recipe-ingredients : Updates an existing recipeIngredient.
     *
     * @param recipeIngredientDTO the recipeIngredientDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recipeIngredientDTO,
     * or with status 400 (Bad Request) if the recipeIngredientDTO is not valid,
     * or with status 500 (Internal Server Error) if the recipeIngredientDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recipe-ingredients")
    @Timed
    public ResponseEntity<RecipeIngredientDTO> updateRecipeIngredient(@RequestBody RecipeIngredientDTO recipeIngredientDTO) throws URISyntaxException {
        log.debug("REST request to update RecipeIngredient : {}", recipeIngredientDTO);
        if (recipeIngredientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RecipeIngredientDTO result = recipeIngredientService.save(recipeIngredientDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recipeIngredientDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recipe-ingredients : get all the recipeIngredients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recipeIngredients in body
     */
    @GetMapping("/recipe-ingredients")
    @Timed
    public ResponseEntity<List<RecipeIngredientDTO>> getAllRecipeIngredients(Pageable pageable) {
        log.debug("REST request to get a page of RecipeIngredients");
        Page<RecipeIngredientDTO> page = recipeIngredientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recipe-ingredients");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /recipe-ingredients/:id : get the "id" recipeIngredient.
     *
     * @param id the id of the recipeIngredientDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recipeIngredientDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recipe-ingredients/{id}")
    @Timed
    public ResponseEntity<RecipeIngredientDTO> getRecipeIngredient(@PathVariable Long id) {
        log.debug("REST request to get RecipeIngredient : {}", id);
        Optional<RecipeIngredientDTO> recipeIngredientDTO = recipeIngredientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recipeIngredientDTO);
    }

    /**
     * DELETE  /recipe-ingredients/:id : delete the "id" recipeIngredient.
     *
     * @param id the id of the recipeIngredientDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recipe-ingredients/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecipeIngredient(@PathVariable Long id) {
        log.debug("REST request to delete RecipeIngredient : {}", id);
        recipeIngredientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
