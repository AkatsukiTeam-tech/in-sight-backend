package com.app.insight.web.rest;

import com.app.insight.repository.MediaFilesRepository;
import com.app.insight.service.MediaFilesService;
import com.app.insight.service.dto.MediaFilesDTO;
import com.app.insight.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.app.insight.domain.MediaFiles}.
 */
@RestController
@RequestMapping("/api")
public class MediaFilesResource {

    private final Logger log = LoggerFactory.getLogger(MediaFilesResource.class);

    private static final String ENTITY_NAME = "mediaFiles";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MediaFilesService mediaFilesService;

    private final MediaFilesRepository mediaFilesRepository;

    public MediaFilesResource(MediaFilesService mediaFilesService, MediaFilesRepository mediaFilesRepository) {
        this.mediaFilesService = mediaFilesService;
        this.mediaFilesRepository = mediaFilesRepository;
    }

    /**
     * {@code POST  /media-files} : Create a new mediaFiles.
     *
     * @param mediaFilesDTO the mediaFilesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mediaFilesDTO, or with status {@code 400 (Bad Request)} if the mediaFiles has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/media-files")
    public ResponseEntity<MediaFilesDTO> createMediaFiles(@RequestBody MediaFilesDTO mediaFilesDTO) throws URISyntaxException {
        log.debug("REST request to save MediaFiles : {}", mediaFilesDTO);
        if (mediaFilesDTO.getId() != null) {
            throw new BadRequestAlertException("A new mediaFiles cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaFilesDTO result = mediaFilesService.save(mediaFilesDTO);
        return ResponseEntity
            .created(new URI("/api/media-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /media-files/:id} : Updates an existing mediaFiles.
     *
     * @param id the id of the mediaFilesDTO to save.
     * @param mediaFilesDTO the mediaFilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mediaFilesDTO,
     * or with status {@code 400 (Bad Request)} if the mediaFilesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mediaFilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/media-files/{id}")
    public ResponseEntity<MediaFilesDTO> updateMediaFiles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MediaFilesDTO mediaFilesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MediaFiles : {}, {}", id, mediaFilesDTO);
        if (mediaFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mediaFilesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mediaFilesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MediaFilesDTO result = mediaFilesService.update(mediaFilesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mediaFilesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /media-files/:id} : Partial updates given fields of an existing mediaFiles, field will ignore if it is null
     *
     * @param id the id of the mediaFilesDTO to save.
     * @param mediaFilesDTO the mediaFilesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mediaFilesDTO,
     * or with status {@code 400 (Bad Request)} if the mediaFilesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mediaFilesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mediaFilesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/media-files/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MediaFilesDTO> partialUpdateMediaFiles(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MediaFilesDTO mediaFilesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MediaFiles partially : {}, {}", id, mediaFilesDTO);
        if (mediaFilesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mediaFilesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mediaFilesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MediaFilesDTO> result = mediaFilesService.partialUpdate(mediaFilesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mediaFilesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /media-files} : get all the mediaFiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mediaFiles in body.
     */
    @GetMapping("/media-files")
    public List<MediaFilesDTO> getAllMediaFiles() {
        log.debug("REST request to get all MediaFiles");
        return mediaFilesService.findAll();
    }

    /**
     * {@code GET  /media-files/:id} : get the "id" mediaFiles.
     *
     * @param id the id of the mediaFilesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mediaFilesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/media-files/{id}")
    public ResponseEntity<MediaFilesDTO> getMediaFiles(@PathVariable Long id) {
        log.debug("REST request to get MediaFiles : {}", id);
        Optional<MediaFilesDTO> mediaFilesDTO = mediaFilesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mediaFilesDTO);
    }

    /**
     * {@code DELETE  /media-files/:id} : delete the "id" mediaFiles.
     *
     * @param id the id of the mediaFilesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/media-files/{id}")
    public ResponseEntity<Void> deleteMediaFiles(@PathVariable Long id) {
        log.debug("REST request to delete MediaFiles : {}", id);
        mediaFilesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
