package com.app.insight.service;

import com.app.insight.domain.TokenBlackList;
import com.app.insight.repository.TokenBlackListRepository;
import com.app.insight.service.dto.TokenBlackListDTO;
import com.app.insight.service.mapper.TokenBlackListMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TokenBlackList}.
 */
@Service
@Transactional
public class TokenBlackListService {

    private final Logger log = LoggerFactory.getLogger(TokenBlackListService.class);

    private final TokenBlackListRepository tokenBlackListRepository;

    private final TokenBlackListMapper tokenBlackListMapper;

    public TokenBlackListService(TokenBlackListRepository tokenBlackListRepository, TokenBlackListMapper tokenBlackListMapper) {
        this.tokenBlackListRepository = tokenBlackListRepository;
        this.tokenBlackListMapper = tokenBlackListMapper;
    }

    /**
     * Save a tokenBlackList.
     *
     * @param tokenBlackListDTO the entity to save.
     * @return the persisted entity.
     */
    public TokenBlackListDTO save(TokenBlackListDTO tokenBlackListDTO) {
        log.debug("Request to save TokenBlackList : {}", tokenBlackListDTO);
        TokenBlackList tokenBlackList = tokenBlackListMapper.toEntity(tokenBlackListDTO);
        tokenBlackList = tokenBlackListRepository.save(tokenBlackList);
        return tokenBlackListMapper.toDto(tokenBlackList);
    }

    /**
     * Update a tokenBlackList.
     *
     * @param tokenBlackListDTO the entity to save.
     * @return the persisted entity.
     */
    public TokenBlackListDTO update(TokenBlackListDTO tokenBlackListDTO) {
        log.debug("Request to update TokenBlackList : {}", tokenBlackListDTO);
        TokenBlackList tokenBlackList = tokenBlackListMapper.toEntity(tokenBlackListDTO);
        tokenBlackList = tokenBlackListRepository.save(tokenBlackList);
        return tokenBlackListMapper.toDto(tokenBlackList);
    }

    /**
     * Partially update a tokenBlackList.
     *
     * @param tokenBlackListDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TokenBlackListDTO> partialUpdate(TokenBlackListDTO tokenBlackListDTO) {
        log.debug("Request to partially update TokenBlackList : {}", tokenBlackListDTO);

        return tokenBlackListRepository
            .findById(tokenBlackListDTO.getId())
            .map(existingTokenBlackList -> {
                tokenBlackListMapper.partialUpdate(existingTokenBlackList, tokenBlackListDTO);

                return existingTokenBlackList;
            })
            .map(tokenBlackListRepository::save)
            .map(tokenBlackListMapper::toDto);
    }

    /**
     * Get all the tokenBlackLists.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TokenBlackListDTO> findAll() {
        log.debug("Request to get all TokenBlackLists");
        return tokenBlackListRepository
            .findAll()
            .stream()
            .map(tokenBlackListMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tokenBlackList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TokenBlackListDTO> findOne(Long id) {
        log.debug("Request to get TokenBlackList : {}", id);
        return tokenBlackListRepository.findById(id).map(tokenBlackListMapper::toDto);
    }

    /**
     * Delete the tokenBlackList by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TokenBlackList : {}", id);
        tokenBlackListRepository.deleteById(id);
    }
}
