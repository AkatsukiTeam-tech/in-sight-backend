package com.app.insight.service;

import com.app.insight.domain.CommentAnswer;
import com.app.insight.repository.CommentAnswerRepository;
import com.app.insight.service.dto.CommentAnswerDTO;
import com.app.insight.service.mapper.CommentAnswerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommentAnswer}.
 */
@Service
@Transactional
public class CommentAnswerService {

    private final Logger log = LoggerFactory.getLogger(CommentAnswerService.class);

    private final CommentAnswerRepository commentAnswerRepository;

    private final CommentAnswerMapper commentAnswerMapper;

    public CommentAnswerService(CommentAnswerRepository commentAnswerRepository, CommentAnswerMapper commentAnswerMapper) {
        this.commentAnswerRepository = commentAnswerRepository;
        this.commentAnswerMapper = commentAnswerMapper;
    }

    /**
     * Save a commentAnswer.
     *
     * @param commentAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentAnswerDTO save(CommentAnswerDTO commentAnswerDTO) {
        log.debug("Request to save CommentAnswer : {}", commentAnswerDTO);
        CommentAnswer commentAnswer = commentAnswerMapper.toEntity(commentAnswerDTO);
        commentAnswer = commentAnswerRepository.save(commentAnswer);
        return commentAnswerMapper.toDto(commentAnswer);
    }

    /**
     * Update a commentAnswer.
     *
     * @param commentAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    public CommentAnswerDTO update(CommentAnswerDTO commentAnswerDTO) {
        log.debug("Request to update CommentAnswer : {}", commentAnswerDTO);
        CommentAnswer commentAnswer = commentAnswerMapper.toEntity(commentAnswerDTO);
        commentAnswer = commentAnswerRepository.save(commentAnswer);
        return commentAnswerMapper.toDto(commentAnswer);
    }

    /**
     * Partially update a commentAnswer.
     *
     * @param commentAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CommentAnswerDTO> partialUpdate(CommentAnswerDTO commentAnswerDTO) {
        log.debug("Request to partially update CommentAnswer : {}", commentAnswerDTO);

        return commentAnswerRepository
            .findById(commentAnswerDTO.getId())
            .map(existingCommentAnswer -> {
                commentAnswerMapper.partialUpdate(existingCommentAnswer, commentAnswerDTO);

                return existingCommentAnswer;
            })
            .map(commentAnswerRepository::save)
            .map(commentAnswerMapper::toDto);
    }

    /**
     * Get all the commentAnswers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CommentAnswerDTO> findAll() {
        log.debug("Request to get all CommentAnswers");
        return commentAnswerRepository.findAll().stream().map(commentAnswerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one commentAnswer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommentAnswerDTO> findOne(Long id) {
        log.debug("Request to get CommentAnswer : {}", id);
        return commentAnswerRepository.findById(id).map(commentAnswerMapper::toDto);
    }

    /**
     * Delete the commentAnswer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommentAnswer : {}", id);
        commentAnswerRepository.deleteById(id);
    }
}
