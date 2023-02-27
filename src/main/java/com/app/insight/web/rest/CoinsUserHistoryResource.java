package com.app.insight.web.rest;

import com.app.insight.repository.CoinsUserHistoryRepository;
import com.app.insight.service.CoinsUserHistoryService;
import com.app.insight.service.dto.CoinsUserHistoryDTO;
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
 * REST controller for managing {@link com.app.insight.domain.CoinsUserHistory}.
 */
@RestController
@RequestMapping("/api")
public class CoinsUserHistoryResource {

    private final Logger log = LoggerFactory.getLogger(CoinsUserHistoryResource.class);

    private static final String ENTITY_NAME = "coinsUserHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CoinsUserHistoryService coinsUserHistoryService;

    private final CoinsUserHistoryRepository coinsUserHistoryRepository;

    public CoinsUserHistoryResource(
        CoinsUserHistoryService coinsUserHistoryService,
        CoinsUserHistoryRepository coinsUserHistoryRepository
    ) {
        this.coinsUserHistoryService = coinsUserHistoryService;
        this.coinsUserHistoryRepository = coinsUserHistoryRepository;
    }

    /**
     * {@code POST  /coins-user-histories} : Create a new coinsUserHistory.
     *
     * @param coinsUserHistoryDTO the coinsUserHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new coinsUserHistoryDTO, or with status {@code 400 (Bad Request)} if the coinsUserHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/coins-user-histories")
    public ResponseEntity<CoinsUserHistoryDTO> createCoinsUserHistory(@RequestBody CoinsUserHistoryDTO coinsUserHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save CoinsUserHistory : {}", coinsUserHistoryDTO);
        if (coinsUserHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new coinsUserHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoinsUserHistoryDTO result = coinsUserHistoryService.save(coinsUserHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/coins-user-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /coins-user-histories/:id} : Updates an existing coinsUserHistory.
     *
     * @param id the id of the coinsUserHistoryDTO to save.
     * @param coinsUserHistoryDTO the coinsUserHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coinsUserHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the coinsUserHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the coinsUserHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/coins-user-histories/{id}")
    public ResponseEntity<CoinsUserHistoryDTO> updateCoinsUserHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CoinsUserHistoryDTO coinsUserHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CoinsUserHistory : {}, {}", id, coinsUserHistoryDTO);
        if (coinsUserHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coinsUserHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coinsUserHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CoinsUserHistoryDTO result = coinsUserHistoryService.update(coinsUserHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coinsUserHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /coins-user-histories/:id} : Partial updates given fields of an existing coinsUserHistory, field will ignore if it is null
     *
     * @param id the id of the coinsUserHistoryDTO to save.
     * @param coinsUserHistoryDTO the coinsUserHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated coinsUserHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the coinsUserHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the coinsUserHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the coinsUserHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/coins-user-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CoinsUserHistoryDTO> partialUpdateCoinsUserHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CoinsUserHistoryDTO coinsUserHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CoinsUserHistory partially : {}, {}", id, coinsUserHistoryDTO);
        if (coinsUserHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, coinsUserHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!coinsUserHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CoinsUserHistoryDTO> result = coinsUserHistoryService.partialUpdate(coinsUserHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, coinsUserHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /coins-user-histories} : get all the coinsUserHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of coinsUserHistories in body.
     */
    @GetMapping("/coins-user-histories")
    public List<CoinsUserHistoryDTO> getAllCoinsUserHistories() {
        log.debug("REST request to get all CoinsUserHistories");
        return coinsUserHistoryService.findAll();
    }

    /**
     * {@code GET  /coins-user-histories/:id} : get the "id" coinsUserHistory.
     *
     * @param id the id of the coinsUserHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the coinsUserHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/coins-user-histories/{id}")
    public ResponseEntity<CoinsUserHistoryDTO> getCoinsUserHistory(@PathVariable Long id) {
        log.debug("REST request to get CoinsUserHistory : {}", id);
        Optional<CoinsUserHistoryDTO> coinsUserHistoryDTO = coinsUserHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(coinsUserHistoryDTO);
    }

    /**
     * {@code DELETE  /coins-user-histories/:id} : delete the "id" coinsUserHistory.
     *
     * @param id the id of the coinsUserHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/coins-user-histories/{id}")
    public ResponseEntity<Void> deleteCoinsUserHistory(@PathVariable Long id) {
        log.debug("REST request to delete CoinsUserHistory : {}", id);
        coinsUserHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
