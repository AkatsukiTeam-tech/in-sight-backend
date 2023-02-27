package com.app.insight.web.rest;

import static com.app.insight.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.insight.IntegrationTest;
import com.app.insight.domain.MediaFiles;
import com.app.insight.domain.enumeration.MediaFilesTypeEnum;
import com.app.insight.repository.MediaFilesRepository;
import com.app.insight.service.dto.MediaFilesDTO;
import com.app.insight.service.mapper.MediaFilesMapper;
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
 * Integration tests for the {@link MediaFilesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MediaFilesResourceIT {

    private static final MediaFilesTypeEnum DEFAULT_TYPE = MediaFilesTypeEnum.UserMaterials;
    private static final MediaFilesTypeEnum UPDATED_TYPE = MediaFilesTypeEnum.EducationMaterials;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTENSIONS = "AAAAAAAAAA";
    private static final String UPDATED_EXTENSIONS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/media-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MediaFilesRepository mediaFilesRepository;

    @Autowired
    private MediaFilesMapper mediaFilesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMediaFilesMockMvc;

    private MediaFiles mediaFiles;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaFiles createEntity(EntityManager em) {
        MediaFiles mediaFiles = new MediaFiles()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .extensions(DEFAULT_EXTENSIONS)
            .location(DEFAULT_LOCATION)
            .createdDate(DEFAULT_CREATED_DATE);
        return mediaFiles;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaFiles createUpdatedEntity(EntityManager em) {
        MediaFiles mediaFiles = new MediaFiles()
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .extensions(UPDATED_EXTENSIONS)
            .location(UPDATED_LOCATION)
            .createdDate(UPDATED_CREATED_DATE);
        return mediaFiles;
    }

    @BeforeEach
    public void initTest() {
        mediaFiles = createEntity(em);
    }

    @Test
    @Transactional
    void createMediaFiles() throws Exception {
        int databaseSizeBeforeCreate = mediaFilesRepository.findAll().size();
        // Create the MediaFiles
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(mediaFiles);
        restMediaFilesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO)))
            .andExpect(status().isCreated());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeCreate + 1);
        MediaFiles testMediaFiles = mediaFilesList.get(mediaFilesList.size() - 1);
        assertThat(testMediaFiles.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMediaFiles.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMediaFiles.getExtensions()).isEqualTo(DEFAULT_EXTENSIONS);
        assertThat(testMediaFiles.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMediaFiles.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void createMediaFilesWithExistingId() throws Exception {
        // Create the MediaFiles with an existing ID
        mediaFiles.setId(1L);
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(mediaFiles);

        int databaseSizeBeforeCreate = mediaFilesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaFilesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMediaFiles() throws Exception {
        // Initialize the database
        mediaFilesRepository.saveAndFlush(mediaFiles);

        // Get all the mediaFilesList
        restMediaFilesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaFiles.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].extensions").value(hasItem(DEFAULT_EXTENSIONS)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }

    @Test
    @Transactional
    void getMediaFiles() throws Exception {
        // Initialize the database
        mediaFilesRepository.saveAndFlush(mediaFiles);

        // Get the mediaFiles
        restMediaFilesMockMvc
            .perform(get(ENTITY_API_URL_ID, mediaFiles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mediaFiles.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.extensions").value(DEFAULT_EXTENSIONS))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingMediaFiles() throws Exception {
        // Get the mediaFiles
        restMediaFilesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMediaFiles() throws Exception {
        // Initialize the database
        mediaFilesRepository.saveAndFlush(mediaFiles);

        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();

        // Update the mediaFiles
        MediaFiles updatedMediaFiles = mediaFilesRepository.findById(mediaFiles.getId()).get();
        // Disconnect from session so that the updates on updatedMediaFiles are not directly saved in db
        em.detach(updatedMediaFiles);
        updatedMediaFiles
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .extensions(UPDATED_EXTENSIONS)
            .location(UPDATED_LOCATION)
            .createdDate(UPDATED_CREATED_DATE);
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(updatedMediaFiles);

        restMediaFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mediaFilesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO))
            )
            .andExpect(status().isOk());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
        MediaFiles testMediaFiles = mediaFilesList.get(mediaFilesList.size() - 1);
        assertThat(testMediaFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMediaFiles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMediaFiles.getExtensions()).isEqualTo(UPDATED_EXTENSIONS);
        assertThat(testMediaFiles.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMediaFiles.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMediaFiles() throws Exception {
        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();
        mediaFiles.setId(count.incrementAndGet());

        // Create the MediaFiles
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(mediaFiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mediaFilesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMediaFiles() throws Exception {
        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();
        mediaFiles.setId(count.incrementAndGet());

        // Create the MediaFiles
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(mediaFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaFilesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMediaFiles() throws Exception {
        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();
        mediaFiles.setId(count.incrementAndGet());

        // Create the MediaFiles
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(mediaFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaFilesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMediaFilesWithPatch() throws Exception {
        // Initialize the database
        mediaFilesRepository.saveAndFlush(mediaFiles);

        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();

        // Update the mediaFiles using partial update
        MediaFiles partialUpdatedMediaFiles = new MediaFiles();
        partialUpdatedMediaFiles.setId(mediaFiles.getId());

        partialUpdatedMediaFiles.type(UPDATED_TYPE).name(UPDATED_NAME);

        restMediaFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMediaFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMediaFiles))
            )
            .andExpect(status().isOk());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
        MediaFiles testMediaFiles = mediaFilesList.get(mediaFilesList.size() - 1);
        assertThat(testMediaFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMediaFiles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMediaFiles.getExtensions()).isEqualTo(DEFAULT_EXTENSIONS);
        assertThat(testMediaFiles.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testMediaFiles.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMediaFilesWithPatch() throws Exception {
        // Initialize the database
        mediaFilesRepository.saveAndFlush(mediaFiles);

        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();

        // Update the mediaFiles using partial update
        MediaFiles partialUpdatedMediaFiles = new MediaFiles();
        partialUpdatedMediaFiles.setId(mediaFiles.getId());

        partialUpdatedMediaFiles
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .extensions(UPDATED_EXTENSIONS)
            .location(UPDATED_LOCATION)
            .createdDate(UPDATED_CREATED_DATE);

        restMediaFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMediaFiles.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMediaFiles))
            )
            .andExpect(status().isOk());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
        MediaFiles testMediaFiles = mediaFilesList.get(mediaFilesList.size() - 1);
        assertThat(testMediaFiles.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMediaFiles.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMediaFiles.getExtensions()).isEqualTo(UPDATED_EXTENSIONS);
        assertThat(testMediaFiles.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testMediaFiles.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMediaFiles() throws Exception {
        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();
        mediaFiles.setId(count.incrementAndGet());

        // Create the MediaFiles
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(mediaFiles);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mediaFilesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMediaFiles() throws Exception {
        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();
        mediaFiles.setId(count.incrementAndGet());

        // Create the MediaFiles
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(mediaFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaFilesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMediaFiles() throws Exception {
        int databaseSizeBeforeUpdate = mediaFilesRepository.findAll().size();
        mediaFiles.setId(count.incrementAndGet());

        // Create the MediaFiles
        MediaFilesDTO mediaFilesDTO = mediaFilesMapper.toDto(mediaFiles);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMediaFilesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mediaFilesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MediaFiles in the database
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMediaFiles() throws Exception {
        // Initialize the database
        mediaFilesRepository.saveAndFlush(mediaFiles);

        int databaseSizeBeforeDelete = mediaFilesRepository.findAll().size();

        // Delete the mediaFiles
        restMediaFilesMockMvc
            .perform(delete(ENTITY_API_URL_ID, mediaFiles.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MediaFiles> mediaFilesList = mediaFilesRepository.findAll();
        assertThat(mediaFilesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
