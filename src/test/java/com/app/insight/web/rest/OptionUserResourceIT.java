package com.app.insight.web.rest;

import static com.app.insight.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.insight.IntegrationTest;
import com.app.insight.domain.OptionUser;
import com.app.insight.repository.OptionUserRepository;
import com.app.insight.service.dto.OptionUserDTO;
import com.app.insight.service.mapper.OptionUserMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link OptionUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OptionUserResourceIT {

    private static final ZonedDateTime DEFAULT_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/option-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OptionUserRepository optionUserRepository;

    @Autowired
    private OptionUserMapper optionUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionUserMockMvc;

    private OptionUser optionUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionUser createEntity(EntityManager em) {
        OptionUser optionUser = new OptionUser().dateTime(DEFAULT_DATE_TIME);
        return optionUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionUser createUpdatedEntity(EntityManager em) {
        OptionUser optionUser = new OptionUser().dateTime(UPDATED_DATE_TIME);
        return optionUser;
    }

    @BeforeEach
    public void initTest() {
        optionUser = createEntity(em);
    }

    @Test
    @Transactional
    void createOptionUser() throws Exception {
        int databaseSizeBeforeCreate = optionUserRepository.findAll().size();
        // Create the OptionUser
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(optionUser);
        restOptionUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionUserDTO)))
            .andExpect(status().isCreated());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeCreate + 1);
        OptionUser testOptionUser = optionUserList.get(optionUserList.size() - 1);
        assertThat(testOptionUser.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
    }

    @Test
    @Transactional
    void createOptionUserWithExistingId() throws Exception {
        // Create the OptionUser with an existing ID
        optionUser.setId(1L);
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(optionUser);

        int databaseSizeBeforeCreate = optionUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptionUsers() throws Exception {
        // Initialize the database
        optionUserRepository.saveAndFlush(optionUser);

        // Get all the optionUserList
        restOptionUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(sameInstant(DEFAULT_DATE_TIME))));
    }

    @Test
    @Transactional
    void getOptionUser() throws Exception {
        // Initialize the database
        optionUserRepository.saveAndFlush(optionUser);

        // Get the optionUser
        restOptionUserMockMvc
            .perform(get(ENTITY_API_URL_ID, optionUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionUser.getId().intValue()))
            .andExpect(jsonPath("$.dateTime").value(sameInstant(DEFAULT_DATE_TIME)));
    }

    @Test
    @Transactional
    void getNonExistingOptionUser() throws Exception {
        // Get the optionUser
        restOptionUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOptionUser() throws Exception {
        // Initialize the database
        optionUserRepository.saveAndFlush(optionUser);

        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();

        // Update the optionUser
        OptionUser updatedOptionUser = optionUserRepository.findById(optionUser.getId()).get();
        // Disconnect from session so that the updates on updatedOptionUser are not directly saved in db
        em.detach(updatedOptionUser);
        updatedOptionUser.dateTime(UPDATED_DATE_TIME);
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(updatedOptionUser);

        restOptionUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
        OptionUser testOptionUser = optionUserList.get(optionUserList.size() - 1);
        assertThat(testOptionUser.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void putNonExistingOptionUser() throws Exception {
        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();
        optionUser.setId(count.incrementAndGet());

        // Create the OptionUser
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(optionUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionUserDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOptionUser() throws Exception {
        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();
        optionUser.setId(count.incrementAndGet());

        // Create the OptionUser
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(optionUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOptionUser() throws Exception {
        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();
        optionUser.setId(count.incrementAndGet());

        // Create the OptionUser
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(optionUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionUserDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOptionUserWithPatch() throws Exception {
        // Initialize the database
        optionUserRepository.saveAndFlush(optionUser);

        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();

        // Update the optionUser using partial update
        OptionUser partialUpdatedOptionUser = new OptionUser();
        partialUpdatedOptionUser.setId(optionUser.getId());

        partialUpdatedOptionUser.dateTime(UPDATED_DATE_TIME);

        restOptionUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionUser))
            )
            .andExpect(status().isOk());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
        OptionUser testOptionUser = optionUserList.get(optionUserList.size() - 1);
        assertThat(testOptionUser.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void fullUpdateOptionUserWithPatch() throws Exception {
        // Initialize the database
        optionUserRepository.saveAndFlush(optionUser);

        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();

        // Update the optionUser using partial update
        OptionUser partialUpdatedOptionUser = new OptionUser();
        partialUpdatedOptionUser.setId(optionUser.getId());

        partialUpdatedOptionUser.dateTime(UPDATED_DATE_TIME);

        restOptionUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionUser))
            )
            .andExpect(status().isOk());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
        OptionUser testOptionUser = optionUserList.get(optionUserList.size() - 1);
        assertThat(testOptionUser.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
    }

    @Test
    @Transactional
    void patchNonExistingOptionUser() throws Exception {
        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();
        optionUser.setId(count.incrementAndGet());

        // Create the OptionUser
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(optionUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, optionUserDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOptionUser() throws Exception {
        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();
        optionUser.setId(count.incrementAndGet());

        // Create the OptionUser
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(optionUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOptionUser() throws Exception {
        int databaseSizeBeforeUpdate = optionUserRepository.findAll().size();
        optionUser.setId(count.incrementAndGet());

        // Create the OptionUser
        OptionUserDTO optionUserDTO = optionUserMapper.toDto(optionUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(optionUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionUser in the database
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOptionUser() throws Exception {
        // Initialize the database
        optionUserRepository.saveAndFlush(optionUser);

        int databaseSizeBeforeDelete = optionUserRepository.findAll().size();

        // Delete the optionUser
        restOptionUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, optionUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptionUser> optionUserList = optionUserRepository.findAll();
        assertThat(optionUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
