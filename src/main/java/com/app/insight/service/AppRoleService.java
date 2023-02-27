package com.app.insight.service;

import com.app.insight.domain.AppRole;
import com.app.insight.domain.enumeration.AppRoleTypeEnum;
import com.app.insight.repository.AppRoleRepository;
import com.app.insight.service.dto.AppRoleDTO;
import com.app.insight.service.mapper.AppRoleMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppRole}.
 */
@Service
@Transactional
public class AppRoleService {

    private final Logger log = LoggerFactory.getLogger(AppRoleService.class);

    private final AppRoleRepository appRoleRepository;

    private final AppRoleMapper appRoleMapper;

    public AppRoleService(AppRoleRepository appRoleRepository, AppRoleMapper appRoleMapper) {
        this.appRoleRepository = appRoleRepository;
        this.appRoleMapper = appRoleMapper;
    }

    /**
     * Save a appRole.
     *
     * @param appRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public AppRoleDTO save(AppRoleDTO appRoleDTO) {
        log.debug("Request to save AppRole : {}", appRoleDTO);
        AppRole appRole = appRoleMapper.toEntity(appRoleDTO);
        appRole = appRoleRepository.save(appRole);
        return appRoleMapper.toDto(appRole);
    }

    /**
     * Update a appRole.
     *
     * @param appRoleDTO the entity to save.
     * @return the persisted entity.
     */
    public AppRoleDTO update(AppRoleDTO appRoleDTO) {
        log.debug("Request to update AppRole : {}", appRoleDTO);
        AppRole appRole = appRoleMapper.toEntity(appRoleDTO);
        appRole = appRoleRepository.save(appRole);
        return appRoleMapper.toDto(appRole);
    }

    /**
     * Partially update a appRole.
     *
     * @param appRoleDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppRoleDTO> partialUpdate(AppRoleDTO appRoleDTO) {
        log.debug("Request to partially update AppRole : {}", appRoleDTO);

        return appRoleRepository
            .findById(appRoleDTO.getId())
            .map(existingAppRole -> {
                appRoleMapper.partialUpdate(existingAppRole, appRoleDTO);

                return existingAppRole;
            })
            .map(appRoleRepository::save)
            .map(appRoleMapper::toDto);
    }

    /**
     * Get all the appRoles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppRoleDTO> findAll() {
        log.debug("Request to get all AppRoles");
        return appRoleRepository.findAll().stream().map(appRoleMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one appRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppRoleDTO> findOne(Long id) {
        log.debug("Request to get AppRole : {}", id);
        return appRoleRepository.findById(id).map(appRoleMapper::toDto);
    }

    @Transactional
    public Optional<AppRoleDTO> findByName(AppRoleTypeEnum role) {
        log.debug("Request to get AppRole by name: {}", role);
        return appRoleRepository.findByName(role).map(appRoleMapper::toDto);
    }

    /**
     * Delete the appRole by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppRole : {}", id);
        appRoleRepository.deleteById(id);
    }
}
