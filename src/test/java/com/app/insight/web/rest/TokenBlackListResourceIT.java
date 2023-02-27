package com.app.insight.web.rest;

import static com.app.insight.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.insight.IntegrationTest;
import com.app.insight.domain.TokenBlackList;
import com.app.insight.domain.enumeration.TokenTypeEnum;
import com.app.insight.repository.TokenBlackListRepository;
import com.app.insight.service.dto.TokenBlackListDTO;
import com.app.insight.service.mapper.TokenBlackListMapper;
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
 * Integration tests for the {@link TokenBlackListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TokenBlackListResourceIT {

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DISPOSE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DISPOSE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final TokenTypeEnum DEFAULT_TYPE = TokenTypeEnum.Access;
    private static final TokenTypeEnum UPDATED_TYPE = TokenTypeEnum.Refresh;

    private static final String ENTITY_API_URL = "/api/token-black-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;

    @Autowired
    private TokenBlackListMapper tokenBlackListMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTokenBlackListMockMvc;

    private TokenBlackList tokenBlackList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TokenBlackList createEntity(EntityManager em) {
        TokenBlackList tokenBlackList = new TokenBlackList().token(DEFAULT_TOKEN).disposeTime(DEFAULT_DISPOSE_TIME).type(DEFAULT_TYPE);
        return tokenBlackList;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TokenBlackList createUpdatedEntity(EntityManager em) {
        TokenBlackList tokenBlackList = new TokenBlackList().token(UPDATED_TOKEN).disposeTime(UPDATED_DISPOSE_TIME).type(UPDATED_TYPE);
        return tokenBlackList;
    }

    @BeforeEach
    public void initTest() {
        tokenBlackList = createEntity(em);
    }

    @Test
    @Transactional
    void createTokenBlackList() throws Exception {
        int databaseSizeBeforeCreate = tokenBlackListRepository.findAll().size();
        // Create the TokenBlackList
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(tokenBlackList);
        restTokenBlackListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeCreate + 1);
        TokenBlackList testTokenBlackList = tokenBlackListList.get(tokenBlackListList.size() - 1);
        assertThat(testTokenBlackList.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testTokenBlackList.getDisposeTime()).isEqualTo(DEFAULT_DISPOSE_TIME);
        assertThat(testTokenBlackList.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createTokenBlackListWithExistingId() throws Exception {
        // Create the TokenBlackList with an existing ID
        tokenBlackList.setId(1L);
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(tokenBlackList);

        int databaseSizeBeforeCreate = tokenBlackListRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTokenBlackListMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTokenBlackLists() throws Exception {
        // Initialize the database
        tokenBlackListRepository.saveAndFlush(tokenBlackList);

        // Get all the tokenBlackListList
        restTokenBlackListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tokenBlackList.getId().intValue())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN)))
            .andExpect(jsonPath("$.[*].disposeTime").value(hasItem(sameInstant(DEFAULT_DISPOSE_TIME))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getTokenBlackList() throws Exception {
        // Initialize the database
        tokenBlackListRepository.saveAndFlush(tokenBlackList);

        // Get the tokenBlackList
        restTokenBlackListMockMvc
            .perform(get(ENTITY_API_URL_ID, tokenBlackList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tokenBlackList.getId().intValue()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN))
            .andExpect(jsonPath("$.disposeTime").value(sameInstant(DEFAULT_DISPOSE_TIME)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTokenBlackList() throws Exception {
        // Get the tokenBlackList
        restTokenBlackListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTokenBlackList() throws Exception {
        // Initialize the database
        tokenBlackListRepository.saveAndFlush(tokenBlackList);

        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();

        // Update the tokenBlackList
        TokenBlackList updatedTokenBlackList = tokenBlackListRepository.findById(tokenBlackList.getId()).get();
        // Disconnect from session so that the updates on updatedTokenBlackList are not directly saved in db
        em.detach(updatedTokenBlackList);
        updatedTokenBlackList.token(UPDATED_TOKEN).disposeTime(UPDATED_DISPOSE_TIME).type(UPDATED_TYPE);
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(updatedTokenBlackList);

        restTokenBlackListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tokenBlackListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isOk());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
        TokenBlackList testTokenBlackList = tokenBlackListList.get(tokenBlackListList.size() - 1);
        assertThat(testTokenBlackList.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testTokenBlackList.getDisposeTime()).isEqualTo(UPDATED_DISPOSE_TIME);
        assertThat(testTokenBlackList.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingTokenBlackList() throws Exception {
        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();
        tokenBlackList.setId(count.incrementAndGet());

        // Create the TokenBlackList
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(tokenBlackList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenBlackListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tokenBlackListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTokenBlackList() throws Exception {
        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();
        tokenBlackList.setId(count.incrementAndGet());

        // Create the TokenBlackList
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(tokenBlackList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTokenBlackListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTokenBlackList() throws Exception {
        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();
        tokenBlackList.setId(count.incrementAndGet());

        // Create the TokenBlackList
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(tokenBlackList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTokenBlackListMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTokenBlackListWithPatch() throws Exception {
        // Initialize the database
        tokenBlackListRepository.saveAndFlush(tokenBlackList);

        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();

        // Update the tokenBlackList using partial update
        TokenBlackList partialUpdatedTokenBlackList = new TokenBlackList();
        partialUpdatedTokenBlackList.setId(tokenBlackList.getId());

        partialUpdatedTokenBlackList.type(UPDATED_TYPE);

        restTokenBlackListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTokenBlackList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTokenBlackList))
            )
            .andExpect(status().isOk());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
        TokenBlackList testTokenBlackList = tokenBlackListList.get(tokenBlackListList.size() - 1);
        assertThat(testTokenBlackList.getToken()).isEqualTo(DEFAULT_TOKEN);
        assertThat(testTokenBlackList.getDisposeTime()).isEqualTo(DEFAULT_DISPOSE_TIME);
        assertThat(testTokenBlackList.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateTokenBlackListWithPatch() throws Exception {
        // Initialize the database
        tokenBlackListRepository.saveAndFlush(tokenBlackList);

        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();

        // Update the tokenBlackList using partial update
        TokenBlackList partialUpdatedTokenBlackList = new TokenBlackList();
        partialUpdatedTokenBlackList.setId(tokenBlackList.getId());

        partialUpdatedTokenBlackList.token(UPDATED_TOKEN).disposeTime(UPDATED_DISPOSE_TIME).type(UPDATED_TYPE);

        restTokenBlackListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTokenBlackList.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTokenBlackList))
            )
            .andExpect(status().isOk());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
        TokenBlackList testTokenBlackList = tokenBlackListList.get(tokenBlackListList.size() - 1);
        assertThat(testTokenBlackList.getToken()).isEqualTo(UPDATED_TOKEN);
        assertThat(testTokenBlackList.getDisposeTime()).isEqualTo(UPDATED_DISPOSE_TIME);
        assertThat(testTokenBlackList.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingTokenBlackList() throws Exception {
        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();
        tokenBlackList.setId(count.incrementAndGet());

        // Create the TokenBlackList
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(tokenBlackList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTokenBlackListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tokenBlackListDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTokenBlackList() throws Exception {
        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();
        tokenBlackList.setId(count.incrementAndGet());

        // Create the TokenBlackList
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(tokenBlackList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTokenBlackListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTokenBlackList() throws Exception {
        int databaseSizeBeforeUpdate = tokenBlackListRepository.findAll().size();
        tokenBlackList.setId(count.incrementAndGet());

        // Create the TokenBlackList
        TokenBlackListDTO tokenBlackListDTO = tokenBlackListMapper.toDto(tokenBlackList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTokenBlackListMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tokenBlackListDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TokenBlackList in the database
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTokenBlackList() throws Exception {
        // Initialize the database
        tokenBlackListRepository.saveAndFlush(tokenBlackList);

        int databaseSizeBeforeDelete = tokenBlackListRepository.findAll().size();

        // Delete the tokenBlackList
        restTokenBlackListMockMvc
            .perform(delete(ENTITY_API_URL_ID, tokenBlackList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TokenBlackList> tokenBlackListList = tokenBlackListRepository.findAll();
        assertThat(tokenBlackListList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
