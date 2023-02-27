package com.app.insight.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.insight.IntegrationTest;
import com.app.insight.domain.AppTest;
import com.app.insight.domain.enumeration.AppTestTypeEnum;
import com.app.insight.repository.AppTestRepository;
import com.app.insight.service.dto.AppTestDTO;
import com.app.insight.service.mapper.AppTestMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AppTestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AppTestResourceIT {

    private static final AppTestTypeEnum DEFAULT_TYPE = AppTestTypeEnum.Prof;
    private static final AppTestTypeEnum UPDATED_TYPE = AppTestTypeEnum.Psy;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/app-tests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AppTestRepository appTestRepository;

    @Autowired
    private AppTestMapper appTestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppTestMockMvc;

    private AppTest appTest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppTest createEntity(EntityManager em) {
        AppTest appTest = new AppTest().type(DEFAULT_TYPE).name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION);
        return appTest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppTest createUpdatedEntity(EntityManager em) {
        AppTest appTest = new AppTest().type(UPDATED_TYPE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        return appTest;
    }

    @BeforeEach
    public void initTest() {
        appTest = createEntity(em);
    }

    @Test
    @Transactional
    void createAppTest() throws Exception {
        int databaseSizeBeforeCreate = appTestRepository.findAll().size();
        // Create the AppTest
        AppTestDTO appTestDTO = appTestMapper.toDto(appTest);
        restAppTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appTestDTO)))
            .andExpect(status().isCreated());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeCreate + 1);
        AppTest testAppTest = appTestList.get(appTestList.size() - 1);
        assertThat(testAppTest.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAppTest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAppTest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createAppTestWithExistingId() throws Exception {
        // Create the AppTest with an existing ID
        appTest.setId(1L);
        AppTestDTO appTestDTO = appTestMapper.toDto(appTest);

        int databaseSizeBeforeCreate = appTestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAppTests() throws Exception {
        // Initialize the database
        appTestRepository.saveAndFlush(appTest);

        // Get all the appTestList
        restAppTestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getAppTest() throws Exception {
        // Initialize the database
        appTestRepository.saveAndFlush(appTest);

        // Get the appTest
        restAppTestMockMvc
            .perform(get(ENTITY_API_URL_ID, appTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appTest.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingAppTest() throws Exception {
        // Get the appTest
        restAppTestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAppTest() throws Exception {
        // Initialize the database
        appTestRepository.saveAndFlush(appTest);

        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();

        // Update the appTest
        AppTest updatedAppTest = appTestRepository.findById(appTest.getId()).get();
        // Disconnect from session so that the updates on updatedAppTest are not directly saved in db
        em.detach(updatedAppTest);
        updatedAppTest.type(UPDATED_TYPE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);
        AppTestDTO appTestDTO = appTestMapper.toDto(updatedAppTest);

        restAppTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appTestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appTestDTO))
            )
            .andExpect(status().isOk());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
        AppTest testAppTest = appTestList.get(appTestList.size() - 1);
        assertThat(testAppTest.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAppTest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppTest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingAppTest() throws Exception {
        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();
        appTest.setId(count.incrementAndGet());

        // Create the AppTest
        AppTestDTO appTestDTO = appTestMapper.toDto(appTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, appTestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAppTest() throws Exception {
        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();
        appTest.setId(count.incrementAndGet());

        // Create the AppTest
        AppTestDTO appTestDTO = appTestMapper.toDto(appTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(appTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAppTest() throws Exception {
        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();
        appTest.setId(count.incrementAndGet());

        // Create the AppTest
        AppTestDTO appTestDTO = appTestMapper.toDto(appTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(appTestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAppTestWithPatch() throws Exception {
        // Initialize the database
        appTestRepository.saveAndFlush(appTest);

        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();

        // Update the appTest using partial update
        AppTest partialUpdatedAppTest = new AppTest();
        partialUpdatedAppTest.setId(appTest.getId());

        partialUpdatedAppTest.name(UPDATED_NAME);

        restAppTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppTest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppTest))
            )
            .andExpect(status().isOk());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
        AppTest testAppTest = appTestList.get(appTestList.size() - 1);
        assertThat(testAppTest.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAppTest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppTest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateAppTestWithPatch() throws Exception {
        // Initialize the database
        appTestRepository.saveAndFlush(appTest);

        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();

        // Update the appTest using partial update
        AppTest partialUpdatedAppTest = new AppTest();
        partialUpdatedAppTest.setId(appTest.getId());

        partialUpdatedAppTest.type(UPDATED_TYPE).name(UPDATED_NAME).description(UPDATED_DESCRIPTION);

        restAppTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAppTest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAppTest))
            )
            .andExpect(status().isOk());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
        AppTest testAppTest = appTestList.get(appTestList.size() - 1);
        assertThat(testAppTest.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAppTest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAppTest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingAppTest() throws Exception {
        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();
        appTest.setId(count.incrementAndGet());

        // Create the AppTest
        AppTestDTO appTestDTO = appTestMapper.toDto(appTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, appTestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAppTest() throws Exception {
        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();
        appTest.setId(count.incrementAndGet());

        // Create the AppTest
        AppTestDTO appTestDTO = appTestMapper.toDto(appTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(appTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAppTest() throws Exception {
        int databaseSizeBeforeUpdate = appTestRepository.findAll().size();
        appTest.setId(count.incrementAndGet());

        // Create the AppTest
        AppTestDTO appTestDTO = appTestMapper.toDto(appTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAppTestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(appTestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AppTest in the database
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAppTest() throws Exception {
        // Initialize the database
        appTestRepository.saveAndFlush(appTest);

        int databaseSizeBeforeDelete = appTestRepository.findAll().size();

        // Delete the appTest
        restAppTestMockMvc
            .perform(delete(ENTITY_API_URL_ID, appTest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppTest> appTestList = appTestRepository.findAll();
        assertThat(appTestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
