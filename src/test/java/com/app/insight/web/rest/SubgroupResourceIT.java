package com.app.insight.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.insight.IntegrationTest;
import com.app.insight.domain.Subgroup;
import com.app.insight.repository.SubgroupRepository;
import com.app.insight.service.dto.SubgroupDTO;
import com.app.insight.service.mapper.SubgroupMapper;
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
 * Integration tests for the {@link SubgroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubgroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/subgroups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SubgroupRepository subgroupRepository;

    @Autowired
    private SubgroupMapper subgroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubgroupMockMvc;

    private Subgroup subgroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subgroup createEntity(EntityManager em) {
        Subgroup subgroup = new Subgroup().name(DEFAULT_NAME);
        return subgroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subgroup createUpdatedEntity(EntityManager em) {
        Subgroup subgroup = new Subgroup().name(UPDATED_NAME);
        return subgroup;
    }

    @BeforeEach
    public void initTest() {
        subgroup = createEntity(em);
    }

    @Test
    @Transactional
    void createSubgroup() throws Exception {
        int databaseSizeBeforeCreate = subgroupRepository.findAll().size();
        // Create the Subgroup
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(subgroup);
        restSubgroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subgroupDTO)))
            .andExpect(status().isCreated());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeCreate + 1);
        Subgroup testSubgroup = subgroupList.get(subgroupList.size() - 1);
        assertThat(testSubgroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createSubgroupWithExistingId() throws Exception {
        // Create the Subgroup with an existing ID
        subgroup.setId(1L);
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(subgroup);

        int databaseSizeBeforeCreate = subgroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubgroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subgroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubgroups() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        // Get all the subgroupList
        restSubgroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subgroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getSubgroup() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        // Get the subgroup
        restSubgroupMockMvc
            .perform(get(ENTITY_API_URL_ID, subgroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subgroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingSubgroup() throws Exception {
        // Get the subgroup
        restSubgroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubgroup() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();

        // Update the subgroup
        Subgroup updatedSubgroup = subgroupRepository.findById(subgroup.getId()).get();
        // Disconnect from session so that the updates on updatedSubgroup are not directly saved in db
        em.detach(updatedSubgroup);
        updatedSubgroup.name(UPDATED_NAME);
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(updatedSubgroup);

        restSubgroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subgroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subgroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
        Subgroup testSubgroup = subgroupList.get(subgroupList.size() - 1);
        assertThat(testSubgroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingSubgroup() throws Exception {
        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();
        subgroup.setId(count.incrementAndGet());

        // Create the Subgroup
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(subgroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubgroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, subgroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subgroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubgroup() throws Exception {
        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();
        subgroup.setId(count.incrementAndGet());

        // Create the Subgroup
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(subgroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubgroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(subgroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubgroup() throws Exception {
        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();
        subgroup.setId(count.incrementAndGet());

        // Create the Subgroup
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(subgroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubgroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(subgroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubgroupWithPatch() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();

        // Update the subgroup using partial update
        Subgroup partialUpdatedSubgroup = new Subgroup();
        partialUpdatedSubgroup.setId(subgroup.getId());

        partialUpdatedSubgroup.name(UPDATED_NAME);

        restSubgroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubgroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubgroup))
            )
            .andExpect(status().isOk());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
        Subgroup testSubgroup = subgroupList.get(subgroupList.size() - 1);
        assertThat(testSubgroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateSubgroupWithPatch() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();

        // Update the subgroup using partial update
        Subgroup partialUpdatedSubgroup = new Subgroup();
        partialUpdatedSubgroup.setId(subgroup.getId());

        partialUpdatedSubgroup.name(UPDATED_NAME);

        restSubgroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubgroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubgroup))
            )
            .andExpect(status().isOk());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
        Subgroup testSubgroup = subgroupList.get(subgroupList.size() - 1);
        assertThat(testSubgroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingSubgroup() throws Exception {
        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();
        subgroup.setId(count.incrementAndGet());

        // Create the Subgroup
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(subgroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubgroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, subgroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subgroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubgroup() throws Exception {
        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();
        subgroup.setId(count.incrementAndGet());

        // Create the Subgroup
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(subgroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubgroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(subgroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubgroup() throws Exception {
        int databaseSizeBeforeUpdate = subgroupRepository.findAll().size();
        subgroup.setId(count.incrementAndGet());

        // Create the Subgroup
        SubgroupDTO subgroupDTO = subgroupMapper.toDto(subgroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubgroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(subgroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Subgroup in the database
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubgroup() throws Exception {
        // Initialize the database
        subgroupRepository.saveAndFlush(subgroup);

        int databaseSizeBeforeDelete = subgroupRepository.findAll().size();

        // Delete the subgroup
        restSubgroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, subgroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subgroup> subgroupList = subgroupRepository.findAll();
        assertThat(subgroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
