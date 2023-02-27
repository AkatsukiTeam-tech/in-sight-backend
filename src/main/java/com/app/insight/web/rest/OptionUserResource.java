package com.app.insight.web.rest;

import com.app.insight.repository.OptionUserRepository;
import com.app.insight.service.OptionUserService;
import com.app.insight.service.dto.OptionUserDTO;
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
 * REST controller for managing {@link com.app.insight.domain.OptionUser}.
 */
@RestController
@RequestMapping("/api")
public class OptionUserResource {

    private final Logger log = LoggerFactory.getLogger(OptionUserResource.class);

    private static final String ENTITY_NAME = "optionUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OptionUserService optionUserService;

    private final OptionUserRepository optionUserRepository;

    public OptionUserResource(OptionUserService optionUserService, OptionUserRepository optionUserRepository) {
        this.optionUserService = optionUserService;
        this.optionUserRepository = optionUserRepository;
    }

    /**
     * {@code POST  /option-users} : Create a new optionUser.
     *
     * @param optionUserDTO the optionUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new optionUserDTO, or with status {@code 400 (Bad Request)} if the optionUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/option-users")
    public ResponseEntity<OptionUserDTO> createOptionUser(@RequestBody OptionUserDTO optionUserDTO) throws URISyntaxException {
        log.debug("REST request to save OptionUser : {}", optionUserDTO);
        if (optionUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new optionUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OptionUserDTO result = optionUserService.save(optionUserDTO);
        return ResponseEntity
            .created(new URI("/api/option-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /option-users/:id} : Updates an existing optionUser.
     *
     * @param id the id of the optionUserDTO to save.
     * @param optionUserDTO the optionUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionUserDTO,
     * or with status {@code 400 (Bad Request)} if the optionUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the optionUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/option-users/{id}")
    public ResponseEntity<OptionUserDTO> updateOptionUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OptionUserDTO optionUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OptionUser : {}, {}", id, optionUserDTO);
        if (optionUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OptionUserDTO result = optionUserService.update(optionUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /option-users/:id} : Partial updates given fields of an existing optionUser, field will ignore if it is null
     *
     * @param id the id of the optionUserDTO to save.
     * @param optionUserDTO the optionUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated optionUserDTO,
     * or with status {@code 400 (Bad Request)} if the optionUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the optionUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the optionUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/option-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OptionUserDTO> partialUpdateOptionUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OptionUserDTO optionUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OptionUser partially : {}, {}", id, optionUserDTO);
        if (optionUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, optionUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!optionUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OptionUserDTO> result = optionUserService.partialUpdate(optionUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, optionUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /option-users} : get all the optionUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of optionUsers in body.
     */
    @GetMapping("/option-users")
    public List<OptionUserDTO> getAllOptionUsers() {
        log.debug("REST request to get all OptionUsers");
        return optionUserService.findAll();
    }

    /**
     * {@code GET  /option-users/:id} : get the "id" optionUser.
     *
     * @param id the id of the optionUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the optionUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/option-users/{id}")
    public ResponseEntity<OptionUserDTO> getOptionUser(@PathVariable Long id) {
        log.debug("REST request to get OptionUser : {}", id);
        Optional<OptionUserDTO> optionUserDTO = optionUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(optionUserDTO);
    }

    /**
     * {@code DELETE  /option-users/:id} : delete the "id" optionUser.
     *
     * @param id the id of the optionUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/option-users/{id}")
    public ResponseEntity<Void> deleteOptionUser(@PathVariable Long id) {
        log.debug("REST request to delete OptionUser : {}", id);
        optionUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
