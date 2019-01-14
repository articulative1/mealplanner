package com.mealplanner.web.rest;

import com.mealplanner.MealplannerApp;

import com.mealplanner.domain.RecipeIngredient;
import com.mealplanner.repository.RecipeIngredientRepository;
import com.mealplanner.service.RecipeIngredientService;
import com.mealplanner.service.dto.RecipeIngredientDTO;
import com.mealplanner.service.mapper.RecipeIngredientMapper;
import com.mealplanner.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.mealplanner.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mealplanner.domain.enumeration.MeasurementType;
/**
 * Test class for the RecipeIngredientResource REST controller.
 *
 * @see RecipeIngredientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MealplannerApp.class)
public class RecipeIngredientResourceIntTest {

    private static final MeasurementType DEFAULT_MEASUREMENT_TYPE = MeasurementType.TABLESPOON;
    private static final MeasurementType UPDATED_MEASUREMENT_TYPE = MeasurementType.TEASPOON;

    private static final Double DEFAULT_MEASUREMENT_VALUE = 1D;
    private static final Double UPDATED_MEASUREMENT_VALUE = 2D;

    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @Autowired
    private RecipeIngredientMapper recipeIngredientMapper;

    @Autowired
    private RecipeIngredientService recipeIngredientService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRecipeIngredientMockMvc;

    private RecipeIngredient recipeIngredient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipeIngredientResource recipeIngredientResource = new RecipeIngredientResource(recipeIngredientService);
        this.restRecipeIngredientMockMvc = MockMvcBuilders.standaloneSetup(recipeIngredientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RecipeIngredient createEntity(EntityManager em) {
        RecipeIngredient recipeIngredient = new RecipeIngredient()
            .measurementType(DEFAULT_MEASUREMENT_TYPE)
            .measurementValue(DEFAULT_MEASUREMENT_VALUE);
        return recipeIngredient;
    }

    @Before
    public void initTest() {
        recipeIngredient = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipeIngredient() throws Exception {
        int databaseSizeBeforeCreate = recipeIngredientRepository.findAll().size();

        // Create the RecipeIngredient
        RecipeIngredientDTO recipeIngredientDTO = recipeIngredientMapper.toDto(recipeIngredient);
        restRecipeIngredientMockMvc.perform(post("/api/recipe-ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeIngredientDTO)))
            .andExpect(status().isCreated());

        // Validate the RecipeIngredient in the database
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findAll();
        assertThat(recipeIngredientList).hasSize(databaseSizeBeforeCreate + 1);
        RecipeIngredient testRecipeIngredient = recipeIngredientList.get(recipeIngredientList.size() - 1);
        assertThat(testRecipeIngredient.getMeasurementType()).isEqualTo(DEFAULT_MEASUREMENT_TYPE);
        assertThat(testRecipeIngredient.getMeasurementValue()).isEqualTo(DEFAULT_MEASUREMENT_VALUE);
    }

    @Test
    @Transactional
    public void createRecipeIngredientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeIngredientRepository.findAll().size();

        // Create the RecipeIngredient with an existing ID
        recipeIngredient.setId(1L);
        RecipeIngredientDTO recipeIngredientDTO = recipeIngredientMapper.toDto(recipeIngredient);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeIngredientMockMvc.perform(post("/api/recipe-ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeIngredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeIngredient in the database
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findAll();
        assertThat(recipeIngredientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecipeIngredients() throws Exception {
        // Initialize the database
        recipeIngredientRepository.saveAndFlush(recipeIngredient);

        // Get all the recipeIngredientList
        restRecipeIngredientMockMvc.perform(get("/api/recipe-ingredients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipeIngredient.getId().intValue())))
            .andExpect(jsonPath("$.[*].measurementType").value(hasItem(DEFAULT_MEASUREMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].measurementValue").value(hasItem(DEFAULT_MEASUREMENT_VALUE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getRecipeIngredient() throws Exception {
        // Initialize the database
        recipeIngredientRepository.saveAndFlush(recipeIngredient);

        // Get the recipeIngredient
        restRecipeIngredientMockMvc.perform(get("/api/recipe-ingredients/{id}", recipeIngredient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipeIngredient.getId().intValue()))
            .andExpect(jsonPath("$.measurementType").value(DEFAULT_MEASUREMENT_TYPE.toString()))
            .andExpect(jsonPath("$.measurementValue").value(DEFAULT_MEASUREMENT_VALUE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRecipeIngredient() throws Exception {
        // Get the recipeIngredient
        restRecipeIngredientMockMvc.perform(get("/api/recipe-ingredients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipeIngredient() throws Exception {
        // Initialize the database
        recipeIngredientRepository.saveAndFlush(recipeIngredient);

        int databaseSizeBeforeUpdate = recipeIngredientRepository.findAll().size();

        // Update the recipeIngredient
        RecipeIngredient updatedRecipeIngredient = recipeIngredientRepository.findById(recipeIngredient.getId()).get();
        // Disconnect from session so that the updates on updatedRecipeIngredient are not directly saved in db
        em.detach(updatedRecipeIngredient);
        updatedRecipeIngredient
            .measurementType(UPDATED_MEASUREMENT_TYPE)
            .measurementValue(UPDATED_MEASUREMENT_VALUE);
        RecipeIngredientDTO recipeIngredientDTO = recipeIngredientMapper.toDto(updatedRecipeIngredient);

        restRecipeIngredientMockMvc.perform(put("/api/recipe-ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeIngredientDTO)))
            .andExpect(status().isOk());

        // Validate the RecipeIngredient in the database
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findAll();
        assertThat(recipeIngredientList).hasSize(databaseSizeBeforeUpdate);
        RecipeIngredient testRecipeIngredient = recipeIngredientList.get(recipeIngredientList.size() - 1);
        assertThat(testRecipeIngredient.getMeasurementType()).isEqualTo(UPDATED_MEASUREMENT_TYPE);
        assertThat(testRecipeIngredient.getMeasurementValue()).isEqualTo(UPDATED_MEASUREMENT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipeIngredient() throws Exception {
        int databaseSizeBeforeUpdate = recipeIngredientRepository.findAll().size();

        // Create the RecipeIngredient
        RecipeIngredientDTO recipeIngredientDTO = recipeIngredientMapper.toDto(recipeIngredient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipeIngredientMockMvc.perform(put("/api/recipe-ingredients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipeIngredientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RecipeIngredient in the database
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findAll();
        assertThat(recipeIngredientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipeIngredient() throws Exception {
        // Initialize the database
        recipeIngredientRepository.saveAndFlush(recipeIngredient);

        int databaseSizeBeforeDelete = recipeIngredientRepository.findAll().size();

        // Get the recipeIngredient
        restRecipeIngredientMockMvc.perform(delete("/api/recipe-ingredients/{id}", recipeIngredient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RecipeIngredient> recipeIngredientList = recipeIngredientRepository.findAll();
        assertThat(recipeIngredientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeIngredient.class);
        RecipeIngredient recipeIngredient1 = new RecipeIngredient();
        recipeIngredient1.setId(1L);
        RecipeIngredient recipeIngredient2 = new RecipeIngredient();
        recipeIngredient2.setId(recipeIngredient1.getId());
        assertThat(recipeIngredient1).isEqualTo(recipeIngredient2);
        recipeIngredient2.setId(2L);
        assertThat(recipeIngredient1).isNotEqualTo(recipeIngredient2);
        recipeIngredient1.setId(null);
        assertThat(recipeIngredient1).isNotEqualTo(recipeIngredient2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecipeIngredientDTO.class);
        RecipeIngredientDTO recipeIngredientDTO1 = new RecipeIngredientDTO();
        recipeIngredientDTO1.setId(1L);
        RecipeIngredientDTO recipeIngredientDTO2 = new RecipeIngredientDTO();
        assertThat(recipeIngredientDTO1).isNotEqualTo(recipeIngredientDTO2);
        recipeIngredientDTO2.setId(recipeIngredientDTO1.getId());
        assertThat(recipeIngredientDTO1).isEqualTo(recipeIngredientDTO2);
        recipeIngredientDTO2.setId(2L);
        assertThat(recipeIngredientDTO1).isNotEqualTo(recipeIngredientDTO2);
        recipeIngredientDTO1.setId(null);
        assertThat(recipeIngredientDTO1).isNotEqualTo(recipeIngredientDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recipeIngredientMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recipeIngredientMapper.fromId(null)).isNull();
    }
}
