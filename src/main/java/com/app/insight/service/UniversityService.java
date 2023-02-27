package com.app.insight.service;

import com.app.insight.domain.University;
import com.app.insight.repository.UniversityRepository;
import com.app.insight.service.dto.UniversityDTO;
import com.app.insight.service.mapper.UniversityMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link University}.
 */
@Service
@Transactional
public class UniversityService {

    private final Logger log = LoggerFactory.getLogger(UniversityService.class);

    private final UniversityRepository universityRepository;

    private final UniversityMapper universityMapper;

    public UniversityService(UniversityRepository universityRepository, UniversityMapper universityMapper) {
        this.universityRepository = universityRepository;
        this.universityMapper = universityMapper;
    }

    /**
     * Save a university.
     *
     * @param universityDTO the entity to save.
     * @return the persisted entity.
     */
    public UniversityDTO save(UniversityDTO universityDTO) {
        log.debug("Request to save University : {}", universityDTO);
        University university = universityMapper.toEntity(universityDTO);
        university = universityRepository.save(university);
        return universityMapper.toDto(university);
    }

    /**
     * Update a university.
     *
     * @param universityDTO the entity to save.
     * @return the persisted entity.
     */
    public UniversityDTO update(UniversityDTO universityDTO) {
        log.debug("Request to update University : {}", universityDTO);
        University university = universityMapper.toEntity(universityDTO);
        university = universityRepository.save(university);
        return universityMapper.toDto(university);
    }

    /**
     * Partially update a university.
     *
     * @param universityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UniversityDTO> partialUpdate(UniversityDTO universityDTO) {
        log.debug("Request to partially update University : {}", universityDTO);

        return universityRepository
            .findById(universityDTO.getId())
            .map(existingUniversity -> {
                universityMapper.partialUpdate(existingUniversity, universityDTO);

                return existingUniversity;
            })
            .map(universityRepository::save)
            .map(universityMapper::toDto);
    }

    /**
     * Get all the universities.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UniversityDTO> findAll() {
        log.debug("Request to get all Universities");
        return universityRepository.findAll().stream().map(universityMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the universities with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UniversityDTO> findAllWithEagerRelationships(Pageable pageable) {
        return universityRepository.findAllWithEagerRelationships(pageable).map(universityMapper::toDto);
    }

    /**
     * Get one university by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UniversityDTO> findOne(Long id) {
        log.debug("Request to get University : {}", id);
        return universityRepository.findOneWithEagerRelationships(id).map(universityMapper::toDto);
    }

    /**
     * Delete the university by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete University : {}", id);
        universityRepository.deleteById(id);
    }
}
