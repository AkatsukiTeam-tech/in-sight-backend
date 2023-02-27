package com.app.insight.web.rest;

import com.app.insight.repository.ParentsNumberRepository;
import com.app.insight.service.ParentsNumberService;
import com.app.insight.service.dto.ParentsNumberDTO;
import com.app.insight.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.app.insight.domain.ParentsNumber}.
 */
@RestController
@RequestMapping("/api")
public class ParentsNumberResource {

    private final Logger log = LoggerFactory.getLogger(ParentsNumberResource.class);

    private static final String ENTITY_NAME = "parentsNumber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParentsNumberService parentsNumberService;

    private final ParentsNumberRepository parentsNumberRepository;

    public ParentsNumberResource(ParentsNumberService parentsNumberService, ParentsNumberRepository parentsNumberRepository) {
        this.parentsNumberService = parentsNumberService;
        this.parentsNumberRepository = parentsNumberRepository;
    }

    /**
     * {@code POST  /parents-numbers} : Create a new parentsNumber.
     *
     * @param parentsNumberDTO the parentsNumberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parentsNumberDTO, or with status {@code 400 (Bad Request)} if the parentsNumber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/parents-numbers")
    public ResponseEntity<ParentsNumberDTO> createParentsNumber(@Valid @RequestBody ParentsNumberDTO parentsNumberDTO)
        throws URISyntaxException {
        log.debug("REST request to save ParentsNumber : {}", parentsNumberDTO);
        if (parentsNumberDTO.getId() != null) {
            throw new BadRequestAlertException("A new parentsNumber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParentsNumberDTO result = parentsNumberService.save(parentsNumberDTO);
        return ResponseEntity
            .created(new URI("/api/parents-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /parents-numbers/:id} : Updates an existing parentsNumber.
     *
     * @param id the id of the parentsNumberDTO to save.
     * @param parentsNumberDTO the parentsNumberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parentsNumberDTO,
     * or with status {@code 400 (Bad Request)} if the parentsNumberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parentsNumberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/parents-numbers/{id}")
    public ResponseEntity<ParentsNumberDTO> updateParentsNumber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ParentsNumberDTO parentsNumberDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ParentsNumber : {}, {}", id, parentsNumberDTO);
        if (parentsNumberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parentsNumberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parentsNumberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ParentsNumberDTO result = parentsNumberService.update(parentsNumberDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parentsNumberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /parents-numbers/:id} : Partial updates given fields of an existing parentsNumber, field will ignore if it is null
     *
     * @param id the id of the parentsNumberDTO to save.
     * @param parentsNumberDTO the parentsNumberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parentsNumberDTO,
     * or with status {@code 400 (Bad Request)} if the parentsNumberDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parentsNumberDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parentsNumberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/parents-numbers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParentsNumberDTO> partialUpdateParentsNumber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ParentsNumberDTO parentsNumberDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ParentsNumber partially : {}, {}", id, parentsNumberDTO);
        if (parentsNumberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, parentsNumberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parentsNumberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParentsNumberDTO> result = parentsNumberService.partialUpdate(parentsNumberDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parentsNumberDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /parents-numbers} : get all the parentsNumbers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parentsNumbers in body.
     */
    @GetMapping("/parents-numbers")
    public List<ParentsNumberDTO> getAllParentsNumbers() {
        log.debug("REST request to get all ParentsNumbers");
        return parentsNumberService.findAll();
    }

    /**
     * {@code GET  /parents-numbers/:id} : get the "id" parentsNumber.
     *
     * @param id the id of the parentsNumberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parentsNumberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/parents-numbers/{id}")
    public ResponseEntity<ParentsNumberDTO> getParentsNumber(@PathVariable Long id) {
        log.debug("REST request to get ParentsNumber : {}", id);
        Optional<ParentsNumberDTO> parentsNumberDTO = parentsNumberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parentsNumberDTO);
    }

    /**
     * {@code DELETE  /parents-numbers/:id} : delete the "id" parentsNumber.
     *
     * @param id the id of the parentsNumberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/parents-numbers/{id}")
    public ResponseEntity<Void> deleteParentsNumber(@PathVariable Long id) {
        log.debug("REST request to delete ParentsNumber : {}", id);
        parentsNumberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
