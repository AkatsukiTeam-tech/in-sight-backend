package com.app.insight.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.insight.IntegrationTest;
import com.app.insight.domain.ParentsNumber;
import com.app.insight.domain.enumeration.ParentsEnum;
import com.app.insight.repository.ParentsNumberRepository;
import com.app.insight.service.dto.ParentsNumberDTO;
import com.app.insight.service.mapper.ParentsNumberMapper;
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
 * Integration tests for the {@link ParentsNumberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParentsNumberResourceIT {

    private static final ParentsEnum DEFAULT_ROLE = ParentsEnum.Mother;
    private static final ParentsEnum UPDATED_ROLE = ParentsEnum.Father;

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/parents-numbers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ParentsNumberRepository parentsNumberRepository;

    @Autowired
    private ParentsNumberMapper parentsNumberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParentsNumberMockMvc;

    private ParentsNumber parentsNumber;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParentsNumber createEntity(EntityManager em) {
        ParentsNumber parentsNumber = new ParentsNumber().role(DEFAULT_ROLE).number(DEFAULT_NUMBER);
        return parentsNumber;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParentsNumber createUpdatedEntity(EntityManager em) {
        ParentsNumber parentsNumber = new ParentsNumber().role(UPDATED_ROLE).number(UPDATED_NUMBER);
        return parentsNumber;
    }

    @BeforeEach
    public void initTest() {
        parentsNumber = createEntity(em);
    }

    @Test
    @Transactional
    void createParentsNumber() throws Exception {
        int databaseSizeBeforeCreate = parentsNumberRepository.findAll().size();
        // Create the ParentsNumber
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);
        restParentsNumberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeCreate + 1);
        ParentsNumber testParentsNumber = parentsNumberList.get(parentsNumberList.size() - 1);
        assertThat(testParentsNumber.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testParentsNumber.getNumber()).isEqualTo(DEFAULT_NUMBER);
    }

    @Test
    @Transactional
    void createParentsNumberWithExistingId() throws Exception {
        // Create the ParentsNumber with an existing ID
        parentsNumber.setId(1L);
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        int databaseSizeBeforeCreate = parentsNumberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restParentsNumberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentsNumberRepository.findAll().size();
        // set the field null
        parentsNumber.setRole(null);

        // Create the ParentsNumber, which fails.
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        restParentsNumberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = parentsNumberRepository.findAll().size();
        // set the field null
        parentsNumber.setNumber(null);

        // Create the ParentsNumber, which fails.
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        restParentsNumberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isBadRequest());

        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllParentsNumbers() throws Exception {
        // Initialize the database
        parentsNumberRepository.saveAndFlush(parentsNumber);

        // Get all the parentsNumberList
        restParentsNumberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parentsNumber.getId().intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)));
    }

    @Test
    @Transactional
    void getParentsNumber() throws Exception {
        // Initialize the database
        parentsNumberRepository.saveAndFlush(parentsNumber);

        // Get the parentsNumber
        restParentsNumberMockMvc
            .perform(get(ENTITY_API_URL_ID, parentsNumber.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(parentsNumber.getId().intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingParentsNumber() throws Exception {
        // Get the parentsNumber
        restParentsNumberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingParentsNumber() throws Exception {
        // Initialize the database
        parentsNumberRepository.saveAndFlush(parentsNumber);

        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();

        // Update the parentsNumber
        ParentsNumber updatedParentsNumber = parentsNumberRepository.findById(parentsNumber.getId()).get();
        // Disconnect from session so that the updates on updatedParentsNumber are not directly saved in db
        em.detach(updatedParentsNumber);
        updatedParentsNumber.role(UPDATED_ROLE).number(UPDATED_NUMBER);
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(updatedParentsNumber);

        restParentsNumberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parentsNumberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isOk());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
        ParentsNumber testParentsNumber = parentsNumberList.get(parentsNumberList.size() - 1);
        assertThat(testParentsNumber.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testParentsNumber.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingParentsNumber() throws Exception {
        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();
        parentsNumber.setId(count.incrementAndGet());

        // Create the ParentsNumber
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParentsNumberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, parentsNumberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchParentsNumber() throws Exception {
        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();
        parentsNumber.setId(count.incrementAndGet());

        // Create the ParentsNumber
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentsNumberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamParentsNumber() throws Exception {
        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();
        parentsNumber.setId(count.incrementAndGet());

        // Create the ParentsNumber
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentsNumberMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateParentsNumberWithPatch() throws Exception {
        // Initialize the database
        parentsNumberRepository.saveAndFlush(parentsNumber);

        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();

        // Update the parentsNumber using partial update
        ParentsNumber partialUpdatedParentsNumber = new ParentsNumber();
        partialUpdatedParentsNumber.setId(parentsNumber.getId());

        partialUpdatedParentsNumber.role(UPDATED_ROLE).number(UPDATED_NUMBER);

        restParentsNumberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParentsNumber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParentsNumber))
            )
            .andExpect(status().isOk());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
        ParentsNumber testParentsNumber = parentsNumberList.get(parentsNumberList.size() - 1);
        assertThat(testParentsNumber.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testParentsNumber.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateParentsNumberWithPatch() throws Exception {
        // Initialize the database
        parentsNumberRepository.saveAndFlush(parentsNumber);

        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();

        // Update the parentsNumber using partial update
        ParentsNumber partialUpdatedParentsNumber = new ParentsNumber();
        partialUpdatedParentsNumber.setId(parentsNumber.getId());

        partialUpdatedParentsNumber.role(UPDATED_ROLE).number(UPDATED_NUMBER);

        restParentsNumberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedParentsNumber.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParentsNumber))
            )
            .andExpect(status().isOk());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
        ParentsNumber testParentsNumber = parentsNumberList.get(parentsNumberList.size() - 1);
        assertThat(testParentsNumber.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testParentsNumber.getNumber()).isEqualTo(UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingParentsNumber() throws Exception {
        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();
        parentsNumber.setId(count.incrementAndGet());

        // Create the ParentsNumber
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParentsNumberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, parentsNumberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchParentsNumber() throws Exception {
        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();
        parentsNumber.setId(count.incrementAndGet());

        // Create the ParentsNumber
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentsNumberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamParentsNumber() throws Exception {
        int databaseSizeBeforeUpdate = parentsNumberRepository.findAll().size();
        parentsNumber.setId(count.incrementAndGet());

        // Create the ParentsNumber
        ParentsNumberDTO parentsNumberDTO = parentsNumberMapper.toDto(parentsNumber);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restParentsNumberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(parentsNumberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ParentsNumber in the database
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteParentsNumber() throws Exception {
        // Initialize the database
        parentsNumberRepository.saveAndFlush(parentsNumber);

        int databaseSizeBeforeDelete = parentsNumberRepository.findAll().size();

        // Delete the parentsNumber
        restParentsNumberMockMvc
            .perform(delete(ENTITY_API_URL_ID, parentsNumber.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParentsNumber> parentsNumberList = parentsNumberRepository.findAll();
        assertThat(parentsNumberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
