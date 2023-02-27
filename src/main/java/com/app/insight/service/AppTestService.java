package com.app.insight.service;

import com.app.insight.domain.AppTest;
import com.app.insight.repository.AppTestRepository;
import com.app.insight.service.dto.AppTestDTO;
import com.app.insight.service.mapper.AppTestMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppTest}.
 */
@Service
@Transactional
public class AppTestService {

    private final Logger log = LoggerFactory.getLogger(AppTestService.class);

    private final AppTestRepository appTestRepository;

    private final AppTestMapper appTestMapper;

    public AppTestService(AppTestRepository appTestRepository, AppTestMapper appTestMapper) {
        this.appTestRepository = appTestRepository;
        this.appTestMapper = appTestMapper;
    }

    /**
     * Save a appTest.
     *
     * @param appTestDTO the entity to save.
     * @return the persisted entity.
     */
    public AppTestDTO save(AppTestDTO appTestDTO) {
        log.debug("Request to save AppTest : {}", appTestDTO);
        AppTest appTest = appTestMapper.toEntity(appTestDTO);
        appTest = appTestRepository.save(appTest);
        return appTestMapper.toDto(appTest);
    }

    /**
     * Update a appTest.
     *
     * @param appTestDTO the entity to save.
     * @return the persisted entity.
     */
    public AppTestDTO update(AppTestDTO appTestDTO) {
        log.debug("Request to update AppTest : {}", appTestDTO);
        AppTest appTest = appTestMapper.toEntity(appTestDTO);
        appTest = appTestRepository.save(appTest);
        return appTestMapper.toDto(appTest);
    }

    /**
     * Partially update a appTest.
     *
     * @param appTestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppTestDTO> partialUpdate(AppTestDTO appTestDTO) {
        log.debug("Request to partially update AppTest : {}", appTestDTO);

        return appTestRepository
            .findById(appTestDTO.getId())
            .map(existingAppTest -> {
                appTestMapper.partialUpdate(existingAppTest, appTestDTO);

                return existingAppTest;
            })
            .map(appTestRepository::save)
            .map(appTestMapper::toDto);
    }

    /**
     * Get all the appTests.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppTestDTO> findAll() {
        log.debug("Request to get all AppTests");
        return appTestRepository.findAll().stream().map(appTestMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one appTest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppTestDTO> findOne(Long id) {
        log.debug("Request to get AppTest : {}", id);
        return appTestRepository.findById(id).map(appTestMapper::toDto);
    }

    /**
     * Delete the appTest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppTest : {}", id);
        appTestRepository.deleteById(id);
    }
}
