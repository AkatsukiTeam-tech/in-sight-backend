package com.app.insight.service;

import com.app.insight.domain.AppUser;
import com.app.insight.repository.AppUserRepository;
import com.app.insight.service.dto.AppUserDTO;
import com.app.insight.service.mapper.AppUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AppUser}.
 */
@Service
@Transactional
public class AppUserService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(AppUserService.class);

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;

    public AppUserService(AppUserRepository appUserRepository, AppUserMapper appUserMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
    }

    /**
     * Save a appUser.
     *
     * @param appUserDTO the entity to save.
     * @return the persisted entity.
     */
    public AppUserDTO save(AppUserDTO appUserDTO) {
        log.debug("Request to save AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    /**
     * Update a appUser.
     *
     * @param appUserDTO the entity to save.
     * @return the persisted entity.
     */
    public AppUserDTO update(AppUserDTO appUserDTO) {
        log.debug("Request to update AppUser : {}", appUserDTO);
        AppUser appUser = appUserMapper.toEntity(appUserDTO);
        appUser = appUserRepository.save(appUser);
        return appUserMapper.toDto(appUser);
    }

    /**
     * Partially update a appUser.
     *
     * @param appUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AppUserDTO> partialUpdate(AppUserDTO appUserDTO) {
        log.debug("Request to partially update AppUser : {}", appUserDTO);

        return appUserRepository
            .findById(appUserDTO.getId())
            .map(existingAppUser -> {
                appUserMapper.partialUpdate(existingAppUser, appUserDTO);

                return existingAppUser;
            })
            .map(appUserRepository::save)
            .map(appUserMapper::toDto);
    }

    /**
     * Get all the appUsers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AppUserDTO> findAll() {
        log.debug("Request to get all AppUsers");
        return appUserRepository.findAll().stream().map(appUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the appUsers with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<AppUserDTO> findAllWithEagerRelationships(Pageable pageable) {
        return appUserRepository.findAllWithEagerRelationships(pageable).map(appUserMapper::toDto);
    }

    /**
     * Get one appUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppUserDTO> findOne(Long id) {
        log.debug("Request to get AppUser : {}", id);
        return appUserRepository.findOneWithEagerRelationships(id).map(appUserMapper::toDto);
    }

    /**
     * Delete the appUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppUser : {}", id);
        appUserRepository.deleteById(id);
    }

    @Transactional
    public Optional<AppUserDTO> findByEmail(String email) {
        log.debug("Request to get AppUser by email : {}", email);
        return appUserRepository.findByEmail(email).map(appUserMapper::toDto);
    }

    @Transactional
    public Optional<AppUserDTO> findByLogin(String login) {
        log.debug("Request to get AppUser by login : {}", login);
        return appUserRepository.findByLogin(login).map(appUserMapper::toDto);
    }

    @Transactional
    public Optional<AppUserDTO> findByPhone(String phone) {
        log.debug("Request to get AppUser by phone : {}", phone);
        return appUserRepository.findByLogin(phone).map(appUserMapper::toDto);
    }

    @Transactional
    public Optional<AppUserDTO> findByIin(String iin) {
        log.debug("Request to get AppUser by iin : {}", iin);
        return appUserRepository.findByLogin(iin).map(appUserMapper::toDto);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<AppUser> appUserOpt = appUserRepository.findByLogin(login);

        log.debug("appUserOpt is empty : {}", appUserOpt.isEmpty());
        if (appUserOpt.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return appUserOpt.get();
    }
}
