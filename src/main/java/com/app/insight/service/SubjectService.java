package com.app.insight.service;

import com.app.insight.domain.Subject;
import com.app.insight.repository.SubjectRepository;
import com.app.insight.service.dto.SubjectDTO;
import com.app.insight.service.mapper.SubjectMapper;
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
 * Service Implementation for managing {@link Subject}.
 */
@Service
@Transactional
public class SubjectService {

    private final Logger log = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepository;

    private final SubjectMapper subjectMapper;

    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
    }

    /**
     * Save a subject.
     *
     * @param subjectDTO the entity to save.
     * @return the persisted entity.
     */
    public SubjectDTO save(SubjectDTO subjectDTO) {
        log.debug("Request to save Subject : {}", subjectDTO);
        Subject subject = subjectMapper.toEntity(subjectDTO);
        subject = subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    /**
     * Update a subject.
     *
     * @param subjectDTO the entity to save.
     * @return the persisted entity.
     */
    public SubjectDTO update(SubjectDTO subjectDTO) {
        log.debug("Request to update Subject : {}", subjectDTO);
        Subject subject = subjectMapper.toEntity(subjectDTO);
        subject = subjectRepository.save(subject);
        return subjectMapper.toDto(subject);
    }

    /**
     * Partially update a subject.
     *
     * @param subjectDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubjectDTO> partialUpdate(SubjectDTO subjectDTO) {
        log.debug("Request to partially update Subject : {}", subjectDTO);

        return subjectRepository
            .findById(subjectDTO.getId())
            .map(existingSubject -> {
                subjectMapper.partialUpdate(existingSubject, subjectDTO);

                return existingSubject;
            })
            .map(subjectRepository::save)
            .map(subjectMapper::toDto);
    }

    /**
     * Get all the subjects.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubjectDTO> findAll() {
        log.debug("Request to get all Subjects");
        return subjectRepository.findAll().stream().map(subjectMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the subjects with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SubjectDTO> findAllWithEagerRelationships(Pageable pageable) {
        return subjectRepository.findAllWithEagerRelationships(pageable).map(subjectMapper::toDto);
    }

    /**
     * Get one subject by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubjectDTO> findOne(Long id) {
        log.debug("Request to get Subject : {}", id);
        return subjectRepository.findOneWithEagerRelationships(id).map(subjectMapper::toDto);
    }

    /**
     * Delete the subject by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Subject : {}", id);
        subjectRepository.deleteById(id);
    }
}
