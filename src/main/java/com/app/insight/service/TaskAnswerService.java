package com.app.insight.service;

import com.app.insight.domain.TaskAnswer;
import com.app.insight.repository.TaskAnswerRepository;
import com.app.insight.service.dto.TaskAnswerDTO;
import com.app.insight.service.mapper.TaskAnswerMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TaskAnswer}.
 */
@Service
@Transactional
public class TaskAnswerService {

    private final Logger log = LoggerFactory.getLogger(TaskAnswerService.class);

    private final TaskAnswerRepository taskAnswerRepository;

    private final TaskAnswerMapper taskAnswerMapper;

    public TaskAnswerService(TaskAnswerRepository taskAnswerRepository, TaskAnswerMapper taskAnswerMapper) {
        this.taskAnswerRepository = taskAnswerRepository;
        this.taskAnswerMapper = taskAnswerMapper;
    }

    /**
     * Save a taskAnswer.
     *
     * @param taskAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskAnswerDTO save(TaskAnswerDTO taskAnswerDTO) {
        log.debug("Request to save TaskAnswer : {}", taskAnswerDTO);
        TaskAnswer taskAnswer = taskAnswerMapper.toEntity(taskAnswerDTO);
        taskAnswer = taskAnswerRepository.save(taskAnswer);
        return taskAnswerMapper.toDto(taskAnswer);
    }

    /**
     * Update a taskAnswer.
     *
     * @param taskAnswerDTO the entity to save.
     * @return the persisted entity.
     */
    public TaskAnswerDTO update(TaskAnswerDTO taskAnswerDTO) {
        log.debug("Request to update TaskAnswer : {}", taskAnswerDTO);
        TaskAnswer taskAnswer = taskAnswerMapper.toEntity(taskAnswerDTO);
        taskAnswer = taskAnswerRepository.save(taskAnswer);
        return taskAnswerMapper.toDto(taskAnswer);
    }

    /**
     * Partially update a taskAnswer.
     *
     * @param taskAnswerDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TaskAnswerDTO> partialUpdate(TaskAnswerDTO taskAnswerDTO) {
        log.debug("Request to partially update TaskAnswer : {}", taskAnswerDTO);

        return taskAnswerRepository
            .findById(taskAnswerDTO.getId())
            .map(existingTaskAnswer -> {
                taskAnswerMapper.partialUpdate(existingTaskAnswer, taskAnswerDTO);

                return existingTaskAnswer;
            })
            .map(taskAnswerRepository::save)
            .map(taskAnswerMapper::toDto);
    }

    /**
     * Get all the taskAnswers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TaskAnswerDTO> findAll() {
        log.debug("Request to get all TaskAnswers");
        return taskAnswerRepository.findAll().stream().map(taskAnswerMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one taskAnswer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskAnswerDTO> findOne(Long id) {
        log.debug("Request to get TaskAnswer : {}", id);
        return taskAnswerRepository.findById(id).map(taskAnswerMapper::toDto);
    }

    /**
     * Delete the taskAnswer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskAnswer : {}", id);
        taskAnswerRepository.deleteById(id);
    }
}
