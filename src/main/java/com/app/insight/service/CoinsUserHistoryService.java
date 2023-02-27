package com.app.insight.service;

import com.app.insight.domain.CoinsUserHistory;
import com.app.insight.repository.CoinsUserHistoryRepository;
import com.app.insight.service.dto.CoinsUserHistoryDTO;
import com.app.insight.service.mapper.CoinsUserHistoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CoinsUserHistory}.
 */
@Service
@Transactional
public class CoinsUserHistoryService {

    private final Logger log = LoggerFactory.getLogger(CoinsUserHistoryService.class);

    private final CoinsUserHistoryRepository coinsUserHistoryRepository;

    private final CoinsUserHistoryMapper coinsUserHistoryMapper;

    public CoinsUserHistoryService(CoinsUserHistoryRepository coinsUserHistoryRepository, CoinsUserHistoryMapper coinsUserHistoryMapper) {
        this.coinsUserHistoryRepository = coinsUserHistoryRepository;
        this.coinsUserHistoryMapper = coinsUserHistoryMapper;
    }

    /**
     * Save a coinsUserHistory.
     *
     * @param coinsUserHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CoinsUserHistoryDTO save(CoinsUserHistoryDTO coinsUserHistoryDTO) {
        log.debug("Request to save CoinsUserHistory : {}", coinsUserHistoryDTO);
        CoinsUserHistory coinsUserHistory = coinsUserHistoryMapper.toEntity(coinsUserHistoryDTO);
        coinsUserHistory = coinsUserHistoryRepository.save(coinsUserHistory);
        return coinsUserHistoryMapper.toDto(coinsUserHistory);
    }

    /**
     * Update a coinsUserHistory.
     *
     * @param coinsUserHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public CoinsUserHistoryDTO update(CoinsUserHistoryDTO coinsUserHistoryDTO) {
        log.debug("Request to update CoinsUserHistory : {}", coinsUserHistoryDTO);
        CoinsUserHistory coinsUserHistory = coinsUserHistoryMapper.toEntity(coinsUserHistoryDTO);
        coinsUserHistory = coinsUserHistoryRepository.save(coinsUserHistory);
        return coinsUserHistoryMapper.toDto(coinsUserHistory);
    }

    /**
     * Partially update a coinsUserHistory.
     *
     * @param coinsUserHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CoinsUserHistoryDTO> partialUpdate(CoinsUserHistoryDTO coinsUserHistoryDTO) {
        log.debug("Request to partially update CoinsUserHistory : {}", coinsUserHistoryDTO);

        return coinsUserHistoryRepository
            .findById(coinsUserHistoryDTO.getId())
            .map(existingCoinsUserHistory -> {
                coinsUserHistoryMapper.partialUpdate(existingCoinsUserHistory, coinsUserHistoryDTO);

                return existingCoinsUserHistory;
            })
            .map(coinsUserHistoryRepository::save)
            .map(coinsUserHistoryMapper::toDto);
    }

    /**
     * Get all the coinsUserHistories.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CoinsUserHistoryDTO> findAll() {
        log.debug("Request to get all CoinsUserHistories");
        return coinsUserHistoryRepository
            .findAll()
            .stream()
            .map(coinsUserHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one coinsUserHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CoinsUserHistoryDTO> findOne(Long id) {
        log.debug("Request to get CoinsUserHistory : {}", id);
        return coinsUserHistoryRepository.findById(id).map(coinsUserHistoryMapper::toDto);
    }

    /**
     * Delete the coinsUserHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CoinsUserHistory : {}", id);
        coinsUserHistoryRepository.deleteById(id);
    }
}
