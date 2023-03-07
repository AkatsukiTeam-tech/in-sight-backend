package com.app.insight.web.rest;

import com.app.insight.repository.SubgroupRepository;
import com.app.insight.service.SubgroupService;
import com.app.insight.service.dto.SubgroupDTO;
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
 * REST controller for managing {@link com.app.insight.domain.Subgroup}.
 */
@RestController
@RequestMapping("/api/admin")
public class SubgroupResource {

    private final Logger log = LoggerFactory.getLogger(SubgroupResource.class);

    private static final String ENTITY_NAME = "subgroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubgroupService subgroupService;

    private final SubgroupRepository subgroupRepository;

    public SubgroupResource(SubgroupService subgroupService, SubgroupRepository subgroupRepository) {
        this.subgroupService = subgroupService;
        this.subgroupRepository = subgroupRepository;
    }

    /**
     * {@code POST  /subgroups} : Create a new subgroup.
     *
     * @param subgroupDTO the subgroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subgroupDTO, or with status {@code 400 (Bad Request)} if the subgroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subgroups")
    public ResponseEntity<SubgroupDTO> createSubgroup(@RequestBody SubgroupDTO subgroupDTO) throws URISyntaxException {
        log.debug("REST request to save Subgroup : {}", subgroupDTO);
        if (subgroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new subgroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubgroupDTO result = subgroupService.save(subgroupDTO);
        return ResponseEntity
            .created(new URI("/api/subgroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subgroups/:id} : Updates an existing subgroup.
     *
     * @param id the id of the subgroupDTO to save.
     * @param subgroupDTO the subgroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subgroupDTO,
     * or with status {@code 400 (Bad Request)} if the subgroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subgroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subgroups/{id}")
    public ResponseEntity<SubgroupDTO> updateSubgroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubgroupDTO subgroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Subgroup : {}, {}", id, subgroupDTO);
        if (subgroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subgroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subgroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubgroupDTO result = subgroupService.update(subgroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subgroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subgroups/:id} : Partial updates given fields of an existing subgroup, field will ignore if it is null
     *
     * @param id the id of the subgroupDTO to save.
     * @param subgroupDTO the subgroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subgroupDTO,
     * or with status {@code 400 (Bad Request)} if the subgroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subgroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subgroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subgroups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubgroupDTO> partialUpdateSubgroup(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubgroupDTO subgroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subgroup partially : {}, {}", id, subgroupDTO);
        if (subgroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subgroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subgroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubgroupDTO> result = subgroupService.partialUpdate(subgroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subgroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /subgroups} : get all the subgroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subgroups in body.
     */
    @GetMapping("/subgroups")
    public List<SubgroupDTO> getAllSubgroups() {
        log.debug("REST request to get all Subgroups");
        return subgroupService.findAll();
    }

    /**
     * {@code GET  /subgroups/:id} : get the "id" subgroup.
     *
     * @param id the id of the subgroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subgroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subgroups/{id}")
    public ResponseEntity<SubgroupDTO> getSubgroup(@PathVariable Long id) {
        log.debug("REST request to get Subgroup : {}", id);
        Optional<SubgroupDTO> subgroupDTO = subgroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subgroupDTO);
    }

    /**
     * {@code DELETE  /subgroups/:id} : delete the "id" subgroup.
     *
     * @param id the id of the subgroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subgroups/{id}")
    public ResponseEntity<Void> deleteSubgroup(@PathVariable Long id) {
        log.debug("REST request to delete Subgroup : {}", id);
        subgroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
