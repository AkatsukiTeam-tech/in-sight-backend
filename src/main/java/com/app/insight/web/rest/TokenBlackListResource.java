package com.app.insight.web.rest;

import com.app.insight.repository.TokenBlackListRepository;
import com.app.insight.service.TokenBlackListService;
import com.app.insight.service.dto.TokenBlackListDTO;
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
 * REST controller for managing {@link com.app.insight.domain.TokenBlackList}.
 */
@RestController
@RequestMapping("/api/admin")
public class TokenBlackListResource {

    private final Logger log = LoggerFactory.getLogger(TokenBlackListResource.class);

    private static final String ENTITY_NAME = "tokenBlackList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TokenBlackListService tokenBlackListService;

    private final TokenBlackListRepository tokenBlackListRepository;

    public TokenBlackListResource(TokenBlackListService tokenBlackListService, TokenBlackListRepository tokenBlackListRepository) {
        this.tokenBlackListService = tokenBlackListService;
        this.tokenBlackListRepository = tokenBlackListRepository;
    }

    /**
     * {@code POST  /token-black-lists} : Create a new tokenBlackList.
     *
     * @param tokenBlackListDTO the tokenBlackListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tokenBlackListDTO, or with status {@code 400 (Bad Request)} if the tokenBlackList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/token-black-lists")
    public ResponseEntity<TokenBlackListDTO> createTokenBlackList(@RequestBody TokenBlackListDTO tokenBlackListDTO)
        throws URISyntaxException {
        log.debug("REST request to save TokenBlackList : {}", tokenBlackListDTO);
        if (tokenBlackListDTO.getId() != null) {
            throw new BadRequestAlertException("A new tokenBlackList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TokenBlackListDTO result = tokenBlackListService.save(tokenBlackListDTO);
        return ResponseEntity
            .created(new URI("/api/token-black-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /token-black-lists/:id} : Updates an existing tokenBlackList.
     *
     * @param id the id of the tokenBlackListDTO to save.
     * @param tokenBlackListDTO the tokenBlackListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tokenBlackListDTO,
     * or with status {@code 400 (Bad Request)} if the tokenBlackListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tokenBlackListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/token-black-lists/{id}")
    public ResponseEntity<TokenBlackListDTO> updateTokenBlackList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TokenBlackListDTO tokenBlackListDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TokenBlackList : {}, {}", id, tokenBlackListDTO);
        if (tokenBlackListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tokenBlackListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tokenBlackListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TokenBlackListDTO result = tokenBlackListService.update(tokenBlackListDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tokenBlackListDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /token-black-lists/:id} : Partial updates given fields of an existing tokenBlackList, field will ignore if it is null
     *
     * @param id the id of the tokenBlackListDTO to save.
     * @param tokenBlackListDTO the tokenBlackListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tokenBlackListDTO,
     * or with status {@code 400 (Bad Request)} if the tokenBlackListDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tokenBlackListDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tokenBlackListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/token-black-lists/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TokenBlackListDTO> partialUpdateTokenBlackList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TokenBlackListDTO tokenBlackListDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TokenBlackList partially : {}, {}", id, tokenBlackListDTO);
        if (tokenBlackListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tokenBlackListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tokenBlackListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TokenBlackListDTO> result = tokenBlackListService.partialUpdate(tokenBlackListDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tokenBlackListDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /token-black-lists} : get all the tokenBlackLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tokenBlackLists in body.
     */
    @GetMapping("/token-black-lists")
    public List<TokenBlackListDTO> getAllTokenBlackLists() {
        log.debug("REST request to get all TokenBlackLists");
        return tokenBlackListService.findAll();
    }

    /**
     * {@code GET  /token-black-lists/:id} : get the "id" tokenBlackList.
     *
     * @param id the id of the tokenBlackListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tokenBlackListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/token-black-lists/{id}")
    public ResponseEntity<TokenBlackListDTO> getTokenBlackList(@PathVariable Long id) {
        log.debug("REST request to get TokenBlackList : {}", id);
        Optional<TokenBlackListDTO> tokenBlackListDTO = tokenBlackListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tokenBlackListDTO);
    }

    /**
     * {@code DELETE  /token-black-lists/:id} : delete the "id" tokenBlackList.
     *
     * @param id the id of the tokenBlackListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/token-black-lists/{id}")
    public ResponseEntity<Void> deleteTokenBlackList(@PathVariable Long id) {
        log.debug("REST request to delete TokenBlackList : {}", id);
        tokenBlackListService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
