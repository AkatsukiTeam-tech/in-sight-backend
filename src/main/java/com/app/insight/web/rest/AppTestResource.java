package com.app.insight.web.rest;

import com.app.insight.repository.AppTestRepository;
import com.app.insight.service.AppTestService;
import com.app.insight.service.dto.AppTestDTO;
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
 * REST controller for managing {@link com.app.insight.domain.AppTest}.
 */
@RestController
@RequestMapping("/api")
public class AppTestResource {

    private final Logger log = LoggerFactory.getLogger(AppTestResource.class);

    private static final String ENTITY_NAME = "appTest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppTestService appTestService;

    private final AppTestRepository appTestRepository;

    public AppTestResource(AppTestService appTestService, AppTestRepository appTestRepository) {
        this.appTestService = appTestService;
        this.appTestRepository = appTestRepository;
    }

    /**
     * {@code POST  /app-tests} : Create a new appTest.
     *
     * @param appTestDTO the appTestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appTestDTO, or with status {@code 400 (Bad Request)} if the appTest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-tests")
    public ResponseEntity<AppTestDTO> createAppTest(@RequestBody AppTestDTO appTestDTO) throws URISyntaxException {
        log.debug("REST request to save AppTest : {}", appTestDTO);
        if (appTestDTO.getId() != null) {
            throw new BadRequestAlertException("A new appTest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppTestDTO result = appTestService.save(appTestDTO);
        return ResponseEntity
            .created(new URI("/api/app-tests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-tests/:id} : Updates an existing appTest.
     *
     * @param id the id of the appTestDTO to save.
     * @param appTestDTO the appTestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appTestDTO,
     * or with status {@code 400 (Bad Request)} if the appTestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appTestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-tests/{id}")
    public ResponseEntity<AppTestDTO> updateAppTest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppTestDTO appTestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AppTest : {}, {}", id, appTestDTO);
        if (appTestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appTestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AppTestDTO result = appTestService.update(appTestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appTestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /app-tests/:id} : Partial updates given fields of an existing appTest, field will ignore if it is null
     *
     * @param id the id of the appTestDTO to save.
     * @param appTestDTO the appTestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appTestDTO,
     * or with status {@code 400 (Bad Request)} if the appTestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the appTestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the appTestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/app-tests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppTestDTO> partialUpdateAppTest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppTestDTO appTestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AppTest partially : {}, {}", id, appTestDTO);
        if (appTestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, appTestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!appTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AppTestDTO> result = appTestService.partialUpdate(appTestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appTestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /app-tests} : get all the appTests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appTests in body.
     */
    @GetMapping("/app-tests")
    public List<AppTestDTO> getAllAppTests() {
        log.debug("REST request to get all AppTests");
        return appTestService.findAll();
    }

    /**
     * {@code GET  /app-tests/:id} : get the "id" appTest.
     *
     * @param id the id of the appTestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appTestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-tests/{id}")
    public ResponseEntity<AppTestDTO> getAppTest(@PathVariable Long id) {
        log.debug("REST request to get AppTest : {}", id);
        Optional<AppTestDTO> appTestDTO = appTestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appTestDTO);
    }

    /**
     * {@code DELETE  /app-tests/:id} : delete the "id" appTest.
     *
     * @param id the id of the appTestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-tests/{id}")
    public ResponseEntity<Void> deleteAppTest(@PathVariable Long id) {
        log.debug("REST request to delete AppTest : {}", id);
        appTestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
