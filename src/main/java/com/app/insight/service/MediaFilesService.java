package com.app.insight.service;

import com.app.insight.domain.MediaFiles;
import com.app.insight.repository.MediaFilesRepository;
import com.app.insight.service.dto.MediaFilesDTO;
import com.app.insight.service.mapper.MediaFilesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MediaFiles}.
 */
@Service
@Transactional
public class MediaFilesService {

    private final Logger log = LoggerFactory.getLogger(MediaFilesService.class);

    private final MediaFilesRepository mediaFilesRepository;

    private final MediaFilesMapper mediaFilesMapper;

    public MediaFilesService(MediaFilesRepository mediaFilesRepository, MediaFilesMapper mediaFilesMapper) {
        this.mediaFilesRepository = mediaFilesRepository;
        this.mediaFilesMapper = mediaFilesMapper;
    }

    /**
     * Save a mediaFiles.
     *
     * @param mediaFilesDTO the entity to save.
     * @return the persisted entity.
     */
    public MediaFilesDTO save(MediaFilesDTO mediaFilesDTO) {
        log.debug("Request to save MediaFiles : {}", mediaFilesDTO);
        MediaFiles mediaFiles = mediaFilesMapper.toEntity(mediaFilesDTO);
        mediaFiles = mediaFilesRepository.save(mediaFiles);
        return mediaFilesMapper.toDto(mediaFiles);
    }

    /**
     * Update a mediaFiles.
     *
     * @param mediaFilesDTO the entity to save.
     * @return the persisted entity.
     */
    public MediaFilesDTO update(MediaFilesDTO mediaFilesDTO) {
        log.debug("Request to update MediaFiles : {}", mediaFilesDTO);
        MediaFiles mediaFiles = mediaFilesMapper.toEntity(mediaFilesDTO);
        mediaFiles = mediaFilesRepository.save(mediaFiles);
        return mediaFilesMapper.toDto(mediaFiles);
    }

    /**
     * Partially update a mediaFiles.
     *
     * @param mediaFilesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MediaFilesDTO> partialUpdate(MediaFilesDTO mediaFilesDTO) {
        log.debug("Request to partially update MediaFiles : {}", mediaFilesDTO);

        return mediaFilesRepository
            .findById(mediaFilesDTO.getId())
            .map(existingMediaFiles -> {
                mediaFilesMapper.partialUpdate(existingMediaFiles, mediaFilesDTO);

                return existingMediaFiles;
            })
            .map(mediaFilesRepository::save)
            .map(mediaFilesMapper::toDto);
    }

    /**
     * Get all the mediaFiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MediaFilesDTO> findAll() {
        log.debug("Request to get all MediaFiles");
        return mediaFilesRepository.findAll().stream().map(mediaFilesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one mediaFiles by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MediaFilesDTO> findOne(UUID id) {
        log.debug("Request to get MediaFiles : {}", id);
        return mediaFilesRepository.findById(id).map(mediaFilesMapper::toDto);
    }

    /**
     * Delete the mediaFiles by id.
     *
     * @param id the id of the entity.
     */
    public void delete(UUID id) {
        log.debug("Request to delete MediaFiles : {}", id);
        mediaFilesRepository.deleteById(id);
    }
}
