package com.app.insight.service;

import com.app.insight.domain.OptionUser;
import com.app.insight.repository.OptionUserRepository;
import com.app.insight.service.dto.OptionUserDTO;
import com.app.insight.service.mapper.OptionUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OptionUser}.
 */
@Service
@Transactional
public class OptionUserService {

    private final Logger log = LoggerFactory.getLogger(OptionUserService.class);

    private final OptionUserRepository optionUserRepository;

    private final OptionUserMapper optionUserMapper;

    public OptionUserService(OptionUserRepository optionUserRepository, OptionUserMapper optionUserMapper) {
        this.optionUserRepository = optionUserRepository;
        this.optionUserMapper = optionUserMapper;
    }

    /**
     * Save a optionUser.
     *
     * @param optionUserDTO the entity to save.
     * @return the persisted entity.
     */
    public OptionUserDTO save(OptionUserDTO optionUserDTO) {
        log.debug("Request to save OptionUser : {}", optionUserDTO);
        OptionUser optionUser = optionUserMapper.toEntity(optionUserDTO);
        optionUser = optionUserRepository.save(optionUser);
        return optionUserMapper.toDto(optionUser);
    }

    /**
     * Update a optionUser.
     *
     * @param optionUserDTO the entity to save.
     * @return the persisted entity.
     */
    public OptionUserDTO update(OptionUserDTO optionUserDTO) {
        log.debug("Request to update OptionUser : {}", optionUserDTO);
        OptionUser optionUser = optionUserMapper.toEntity(optionUserDTO);
        optionUser = optionUserRepository.save(optionUser);
        return optionUserMapper.toDto(optionUser);
    }

    /**
     * Partially update a optionUser.
     *
     * @param optionUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OptionUserDTO> partialUpdate(OptionUserDTO optionUserDTO) {
        log.debug("Request to partially update OptionUser : {}", optionUserDTO);

        return optionUserRepository
            .findById(optionUserDTO.getId())
            .map(existingOptionUser -> {
                optionUserMapper.partialUpdate(existingOptionUser, optionUserDTO);

                return existingOptionUser;
            })
            .map(optionUserRepository::save)
            .map(optionUserMapper::toDto);
    }

    /**
     * Get all the optionUsers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OptionUserDTO> findAll() {
        log.debug("Request to get all OptionUsers");
        return optionUserRepository.findAll().stream().map(optionUserMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one optionUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OptionUserDTO> findOne(Long id) {
        log.debug("Request to get OptionUser : {}", id);
        return optionUserRepository.findById(id).map(optionUserMapper::toDto);
    }

    /**
     * Delete the optionUser by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OptionUser : {}", id);
        optionUserRepository.deleteById(id);
    }
}
