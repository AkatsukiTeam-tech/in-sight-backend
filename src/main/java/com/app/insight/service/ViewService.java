package com.app.insight.service;

import com.app.insight.domain.View;
import com.app.insight.repository.ViewRepository;
import com.app.insight.service.dto.ViewDTO;
import com.app.insight.service.mapper.ViewMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link View}.
 */
@Service
@Transactional
public class ViewService {

    private final Logger log = LoggerFactory.getLogger(ViewService.class);

    private final ViewRepository viewRepository;

    private final ViewMapper viewMapper;

    public ViewService(ViewRepository viewRepository, ViewMapper viewMapper) {
        this.viewRepository = viewRepository;
        this.viewMapper = viewMapper;
    }

    /**
     * Save a view.
     *
     * @param viewDTO the entity to save.
     * @return the persisted entity.
     */
    public ViewDTO save(ViewDTO viewDTO) {
        log.debug("Request to save View : {}", viewDTO);
        View view = viewMapper.toEntity(viewDTO);
        view = viewRepository.save(view);
        return viewMapper.toDto(view);
    }

    /**
     * Update a view.
     *
     * @param viewDTO the entity to save.
     * @return the persisted entity.
     */
    public ViewDTO update(ViewDTO viewDTO) {
        log.debug("Request to update View : {}", viewDTO);
        View view = viewMapper.toEntity(viewDTO);
        view = viewRepository.save(view);
        return viewMapper.toDto(view);
    }

    /**
     * Partially update a view.
     *
     * @param viewDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ViewDTO> partialUpdate(ViewDTO viewDTO) {
        log.debug("Request to partially update View : {}", viewDTO);

        return viewRepository
            .findById(viewDTO.getId())
            .map(existingView -> {
                viewMapper.partialUpdate(existingView, viewDTO);

                return existingView;
            })
            .map(viewRepository::save)
            .map(viewMapper::toDto);
    }

    /**
     * Get all the views.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ViewDTO> findAll() {
        log.debug("Request to get all Views");
        return viewRepository.findAll().stream().map(viewMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one view by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ViewDTO> findOne(Long id) {
        log.debug("Request to get View : {}", id);
        return viewRepository.findById(id).map(viewMapper::toDto);
    }

    /**
     * Delete the view by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete View : {}", id);
        viewRepository.deleteById(id);
    }
}
