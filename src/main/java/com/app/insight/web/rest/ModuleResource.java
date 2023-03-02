package com.app.insight.web.rest;

import com.app.insight.repository.ModuleRepository;
import com.app.insight.service.ModuleService;
import com.app.insight.service.dto.ModuleDTO;
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
 * REST controller for managing {@link com.app.insight.domain.Module}.
 */
@RestController
@RequestMapping("/api/admin")
public class ModuleResource {

    private final Logger log = LoggerFactory.getLogger(ModuleResource.class);

    private static final String ENTITY_NAME = "module";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModuleService moduleService;

    private final ModuleRepository moduleRepository;

    public ModuleResource(ModuleService moduleService, ModuleRepository moduleRepository) {
        this.moduleService = moduleService;
        this.moduleRepository = moduleRepository;
    }

    /**
     * {@code POST  /modules} : Create a new module.
     *
     * @param moduleDTO the moduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new moduleDTO, or with status {@code 400 (Bad Request)} if the module has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modules")
    public ResponseEntity<ModuleDTO> createModule(@RequestBody ModuleDTO moduleDTO) throws URISyntaxException {
        log.debug("REST request to save Module : {}", moduleDTO);
        if (moduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new module cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModuleDTO result = moduleService.save(moduleDTO);
        return ResponseEntity
            .created(new URI("/api/modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modules/:id} : Updates an existing module.
     *
     * @param id the id of the moduleDTO to save.
     * @param moduleDTO the moduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleDTO,
     * or with status {@code 400 (Bad Request)} if the moduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the moduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modules/{id}")
    public ResponseEntity<ModuleDTO> updateModule(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModuleDTO moduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Module : {}, {}", id, moduleDTO);
        if (moduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModuleDTO result = moduleService.update(moduleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /modules/:id} : Partial updates given fields of an existing module, field will ignore if it is null
     *
     * @param id the id of the moduleDTO to save.
     * @param moduleDTO the moduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated moduleDTO,
     * or with status {@code 400 (Bad Request)} if the moduleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the moduleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the moduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/modules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModuleDTO> partialUpdateModule(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModuleDTO moduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Module partially : {}, {}", id, moduleDTO);
        if (moduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, moduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!moduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModuleDTO> result = moduleService.partialUpdate(moduleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, moduleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /modules} : get all the modules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modules in body.
     */
    @GetMapping("/modules")
    public List<ModuleDTO> getAllModules() {
        log.debug("REST request to get all Modules");
        return moduleService.findAll();
    }

    /**
     * {@code GET  /modules/:id} : get the "id" module.
     *
     * @param id the id of the moduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the moduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modules/{id}")
    public ResponseEntity<ModuleDTO> getModule(@PathVariable Long id) {
        log.debug("REST request to get Module : {}", id);
        Optional<ModuleDTO> moduleDTO = moduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(moduleDTO);
    }

    /**
     * {@code DELETE  /modules/:id} : delete the "id" module.
     *
     * @param id the id of the moduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modules/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        log.debug("REST request to delete Module : {}", id);
        moduleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
