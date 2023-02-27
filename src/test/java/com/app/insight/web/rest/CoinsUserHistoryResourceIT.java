package com.app.insight.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.insight.IntegrationTest;
import com.app.insight.domain.CoinsUserHistory;
import com.app.insight.repository.CoinsUserHistoryRepository;
import com.app.insight.service.dto.CoinsUserHistoryDTO;
import com.app.insight.service.mapper.CoinsUserHistoryMapper;
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
 * Integration tests for the {@link CoinsUserHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CoinsUserHistoryResourceIT {

    private static final Integer DEFAULT_COINS = 1;
    private static final Integer UPDATED_COINS = 2;

    private static final String ENTITY_API_URL = "/api/coins-user-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CoinsUserHistoryRepository coinsUserHistoryRepository;

    @Autowired
    private CoinsUserHistoryMapper coinsUserHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCoinsUserHistoryMockMvc;

    private CoinsUserHistory coinsUserHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoinsUserHistory createEntity(EntityManager em) {
        CoinsUserHistory coinsUserHistory = new CoinsUserHistory().coins(DEFAULT_COINS);
        return coinsUserHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CoinsUserHistory createUpdatedEntity(EntityManager em) {
        CoinsUserHistory coinsUserHistory = new CoinsUserHistory().coins(UPDATED_COINS);
        return coinsUserHistory;
    }

    @BeforeEach
    public void initTest() {
        coinsUserHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createCoinsUserHistory() throws Exception {
        int databaseSizeBeforeCreate = coinsUserHistoryRepository.findAll().size();
        // Create the CoinsUserHistory
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(coinsUserHistory);
        restCoinsUserHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        CoinsUserHistory testCoinsUserHistory = coinsUserHistoryList.get(coinsUserHistoryList.size() - 1);
        assertThat(testCoinsUserHistory.getCoins()).isEqualTo(DEFAULT_COINS);
    }

    @Test
    @Transactional
    void createCoinsUserHistoryWithExistingId() throws Exception {
        // Create the CoinsUserHistory with an existing ID
        coinsUserHistory.setId(1L);
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(coinsUserHistory);

        int databaseSizeBeforeCreate = coinsUserHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoinsUserHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCoinsUserHistories() throws Exception {
        // Initialize the database
        coinsUserHistoryRepository.saveAndFlush(coinsUserHistory);

        // Get all the coinsUserHistoryList
        restCoinsUserHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coinsUserHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].coins").value(hasItem(DEFAULT_COINS)));
    }

    @Test
    @Transactional
    void getCoinsUserHistory() throws Exception {
        // Initialize the database
        coinsUserHistoryRepository.saveAndFlush(coinsUserHistory);

        // Get the coinsUserHistory
        restCoinsUserHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, coinsUserHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(coinsUserHistory.getId().intValue()))
            .andExpect(jsonPath("$.coins").value(DEFAULT_COINS));
    }

    @Test
    @Transactional
    void getNonExistingCoinsUserHistory() throws Exception {
        // Get the coinsUserHistory
        restCoinsUserHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCoinsUserHistory() throws Exception {
        // Initialize the database
        coinsUserHistoryRepository.saveAndFlush(coinsUserHistory);

        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();

        // Update the coinsUserHistory
        CoinsUserHistory updatedCoinsUserHistory = coinsUserHistoryRepository.findById(coinsUserHistory.getId()).get();
        // Disconnect from session so that the updates on updatedCoinsUserHistory are not directly saved in db
        em.detach(updatedCoinsUserHistory);
        updatedCoinsUserHistory.coins(UPDATED_COINS);
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(updatedCoinsUserHistory);

        restCoinsUserHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coinsUserHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
        CoinsUserHistory testCoinsUserHistory = coinsUserHistoryList.get(coinsUserHistoryList.size() - 1);
        assertThat(testCoinsUserHistory.getCoins()).isEqualTo(UPDATED_COINS);
    }

    @Test
    @Transactional
    void putNonExistingCoinsUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();
        coinsUserHistory.setId(count.incrementAndGet());

        // Create the CoinsUserHistory
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(coinsUserHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoinsUserHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, coinsUserHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCoinsUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();
        coinsUserHistory.setId(count.incrementAndGet());

        // Create the CoinsUserHistory
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(coinsUserHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoinsUserHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCoinsUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();
        coinsUserHistory.setId(count.incrementAndGet());

        // Create the CoinsUserHistory
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(coinsUserHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoinsUserHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCoinsUserHistoryWithPatch() throws Exception {
        // Initialize the database
        coinsUserHistoryRepository.saveAndFlush(coinsUserHistory);

        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();

        // Update the coinsUserHistory using partial update
        CoinsUserHistory partialUpdatedCoinsUserHistory = new CoinsUserHistory();
        partialUpdatedCoinsUserHistory.setId(coinsUserHistory.getId());

        partialUpdatedCoinsUserHistory.coins(UPDATED_COINS);

        restCoinsUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoinsUserHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoinsUserHistory))
            )
            .andExpect(status().isOk());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
        CoinsUserHistory testCoinsUserHistory = coinsUserHistoryList.get(coinsUserHistoryList.size() - 1);
        assertThat(testCoinsUserHistory.getCoins()).isEqualTo(UPDATED_COINS);
    }

    @Test
    @Transactional
    void fullUpdateCoinsUserHistoryWithPatch() throws Exception {
        // Initialize the database
        coinsUserHistoryRepository.saveAndFlush(coinsUserHistory);

        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();

        // Update the coinsUserHistory using partial update
        CoinsUserHistory partialUpdatedCoinsUserHistory = new CoinsUserHistory();
        partialUpdatedCoinsUserHistory.setId(coinsUserHistory.getId());

        partialUpdatedCoinsUserHistory.coins(UPDATED_COINS);

        restCoinsUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCoinsUserHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCoinsUserHistory))
            )
            .andExpect(status().isOk());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
        CoinsUserHistory testCoinsUserHistory = coinsUserHistoryList.get(coinsUserHistoryList.size() - 1);
        assertThat(testCoinsUserHistory.getCoins()).isEqualTo(UPDATED_COINS);
    }

    @Test
    @Transactional
    void patchNonExistingCoinsUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();
        coinsUserHistory.setId(count.incrementAndGet());

        // Create the CoinsUserHistory
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(coinsUserHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCoinsUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, coinsUserHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCoinsUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();
        coinsUserHistory.setId(count.incrementAndGet());

        // Create the CoinsUserHistory
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(coinsUserHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoinsUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCoinsUserHistory() throws Exception {
        int databaseSizeBeforeUpdate = coinsUserHistoryRepository.findAll().size();
        coinsUserHistory.setId(count.incrementAndGet());

        // Create the CoinsUserHistory
        CoinsUserHistoryDTO coinsUserHistoryDTO = coinsUserHistoryMapper.toDto(coinsUserHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCoinsUserHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(coinsUserHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CoinsUserHistory in the database
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCoinsUserHistory() throws Exception {
        // Initialize the database
        coinsUserHistoryRepository.saveAndFlush(coinsUserHistory);

        int databaseSizeBeforeDelete = coinsUserHistoryRepository.findAll().size();

        // Delete the coinsUserHistory
        restCoinsUserHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, coinsUserHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CoinsUserHistory> coinsUserHistoryList = coinsUserHistoryRepository.findAll();
        assertThat(coinsUserHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
