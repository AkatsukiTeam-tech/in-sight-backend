package com.app.insight.service;

import com.app.insight.domain.Subgroup;
import com.app.insight.repository.SubgroupRepository;
import com.app.insight.service.dto.SubgroupDTO;
import com.app.insight.service.mapper.SubgroupMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Subgroup}.
 */
@Service
@Transactional
public class SubgroupService {

    private final Logger log = LoggerFactory.getLogger(SubgroupService.class);

    private final SubgroupRepository subgroupRepository;

    private final SubgroupMapper subgroupMapper;

    public SubgroupService(SubgroupRepository subgroupRepository, SubgroupMapper subgroupMapper) {
        this.subgroupRepository = subgroupRepository;
        this.subgroupMapper = subgroupMapper;
    }

    /**
     * Save a subgroup.
     *
     * @param subgroupDTO the entity to save.
     * @return the persisted entity.
     */
    public SubgroupDTO save(SubgroupDTO subgroupDTO) {
        log.debug("Request to save Subgroup : {}", subgroupDTO);
        Subgroup subgroup = subgroupMapper.toEntity(subgroupDTO);
        subgroup = subgroupRepository.save(subgroup);
        return subgroupMapper.toDto(subgroup);
    }

    /**
     * Update a subgroup.
     *
     * @param subgroupDTO the entity to save.
     * @return the persisted entity.
     */
    public SubgroupDTO update(SubgroupDTO subgroupDTO) {
        log.debug("Request to update Subgroup : {}", subgroupDTO);
        Subgroup subgroup = subgroupMapper.toEntity(subgroupDTO);
        subgroup = subgroupRepository.save(subgroup);
        return subgroupMapper.toDto(subgroup);
    }

    /**
     * Partially update a subgroup.
     *
     * @param subgroupDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SubgroupDTO> partialUpdate(SubgroupDTO subgroupDTO) {
        log.debug("Request to partially update Subgroup : {}", subgroupDTO);

        return subgroupRepository
            .findById(subgroupDTO.getId())
            .map(existingSubgroup -> {
                subgroupMapper.partialUpdate(existingSubgroup, subgroupDTO);

                return existingSubgroup;
            })
            .map(subgroupRepository::save)
            .map(subgroupMapper::toDto);
    }

    /**
     * Get all the subgroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SubgroupDTO> findAll() {
        log.debug("Request to get all Subgroups");
        return subgroupRepository.findAll().stream().map(subgroupMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one subgroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubgroupDTO> findOne(Long id) {
        log.debug("Request to get Subgroup : {}", id);
        return subgroupRepository.findById(id).map(subgroupMapper::toDto);
    }

    /**
     * Delete the subgroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Subgroup : {}", id);
        subgroupRepository.deleteById(id);
    }
}
