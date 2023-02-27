package com.app.insight.service;

import com.app.insight.domain.Module;
import com.app.insight.repository.ModuleRepository;
import com.app.insight.service.dto.ModuleDTO;
import com.app.insight.service.mapper.ModuleMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Module}.
 */
@Service
@Transactional
public class ModuleService {

    private final Logger log = LoggerFactory.getLogger(ModuleService.class);

    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    public ModuleService(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    /**
     * Save a module.
     *
     * @param moduleDTO the entity to save.
     * @return the persisted entity.
     */
    public ModuleDTO save(ModuleDTO moduleDTO) {
        log.debug("Request to save Module : {}", moduleDTO);
        Module module = moduleMapper.toEntity(moduleDTO);
        module = moduleRepository.save(module);
        return moduleMapper.toDto(module);
    }

    /**
     * Update a module.
     *
     * @param moduleDTO the entity to save.
     * @return the persisted entity.
     */
    public ModuleDTO update(ModuleDTO moduleDTO) {
        log.debug("Request to update Module : {}", moduleDTO);
        Module module = moduleMapper.toEntity(moduleDTO);
        module = moduleRepository.save(module);
        return moduleMapper.toDto(module);
    }

    /**
     * Partially update a module.
     *
     * @param moduleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ModuleDTO> partialUpdate(ModuleDTO moduleDTO) {
        log.debug("Request to partially update Module : {}", moduleDTO);

        return moduleRepository
            .findById(moduleDTO.getId())
            .map(existingModule -> {
                moduleMapper.partialUpdate(existingModule, moduleDTO);

                return existingModule;
            })
            .map(moduleRepository::save)
            .map(moduleMapper::toDto);
    }

    /**
     * Get all the modules.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ModuleDTO> findAll() {
        log.debug("Request to get all Modules");
        return moduleRepository.findAll().stream().map(moduleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one module by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModuleDTO> findOne(Long id) {
        log.debug("Request to get Module : {}", id);
        return moduleRepository.findById(id).map(moduleMapper::toDto);
    }

    /**
     * Delete the module by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Module : {}", id);
        moduleRepository.deleteById(id);
    }
}
