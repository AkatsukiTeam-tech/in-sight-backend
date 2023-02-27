package com.app.insight.service;

import com.app.insight.domain.ParentsNumber;
import com.app.insight.repository.ParentsNumberRepository;
import com.app.insight.service.dto.ParentsNumberDTO;
import com.app.insight.service.mapper.ParentsNumberMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ParentsNumber}.
 */
@Service
@Transactional
public class ParentsNumberService {

    private final Logger log = LoggerFactory.getLogger(ParentsNumberService.class);

    private final ParentsNumberRepository parentsNumberRepository;

    private final ParentsNumberMapper parentsNumberMapper;

    public ParentsNumberService(ParentsNumberRepository parentsNumberRepository, ParentsNumberMapper parentsNumberMapper) {
        this.parentsNumberRepository = parentsNumberRepository;
        this.parentsNumberMapper = parentsNumberMapper;
    }

    /**
     * Save a parentsNumber.
     *
     * @param parentsNumberDTO the entity to save.
     * @return the persisted entity.
     */
    public ParentsNumberDTO save(ParentsNumberDTO parentsNumberDTO) {
        log.debug("Request to save ParentsNumber : {}", parentsNumberDTO);
        ParentsNumber parentsNumber = parentsNumberMapper.toEntity(parentsNumberDTO);
        parentsNumber = parentsNumberRepository.save(parentsNumber);
        return parentsNumberMapper.toDto(parentsNumber);
    }

    /**
     * Update a parentsNumber.
     *
     * @param parentsNumberDTO the entity to save.
     * @return the persisted entity.
     */
    public ParentsNumberDTO update(ParentsNumberDTO parentsNumberDTO) {
        log.debug("Request to update ParentsNumber : {}", parentsNumberDTO);
        ParentsNumber parentsNumber = parentsNumberMapper.toEntity(parentsNumberDTO);
        parentsNumber = parentsNumberRepository.save(parentsNumber);
        return parentsNumberMapper.toDto(parentsNumber);
    }

    /**
     * Partially update a parentsNumber.
     *
     * @param parentsNumberDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ParentsNumberDTO> partialUpdate(ParentsNumberDTO parentsNumberDTO) {
        log.debug("Request to partially update ParentsNumber : {}", parentsNumberDTO);

        return parentsNumberRepository
            .findById(parentsNumberDTO.getId())
            .map(existingParentsNumber -> {
                parentsNumberMapper.partialUpdate(existingParentsNumber, parentsNumberDTO);

                return existingParentsNumber;
            })
            .map(parentsNumberRepository::save)
            .map(parentsNumberMapper::toDto);
    }

    /**
     * Get all the parentsNumbers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ParentsNumberDTO> findAll() {
        log.debug("Request to get all ParentsNumbers");
        return parentsNumberRepository.findAll().stream().map(parentsNumberMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one parentsNumber by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParentsNumberDTO> findOne(Long id) {
        log.debug("Request to get ParentsNumber : {}", id);
        return parentsNumberRepository.findById(id).map(parentsNumberMapper::toDto);
    }

    /**
     * Delete the parentsNumber by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParentsNumber : {}", id);
        parentsNumberRepository.deleteById(id);
    }
}
