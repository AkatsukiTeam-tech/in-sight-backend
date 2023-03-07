package com.app.insight.front.controllers;


import com.app.insight.service.TaskService;
import com.app.insight.service.dto.TaskDTO;
import com.app.insight.web.rest.errors.ObjectNotFoundError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController("TaskController")
@RequestMapping("/api/task")
public class TaskController extends BaseController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private MessageSource messageSource;

    private final Locale locale = LocaleContextHolder.getLocale();

    private final Logger log = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<TaskDTO> save(@RequestBody TaskDTO createTask) {
        return new ResponseEntity<>(taskService.save(createTask), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO updateTask) {
        return new ResponseEntity<>(taskService.update(updateTask), HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TaskDTO>> findAll() {
        List<TaskDTO> tasks = taskService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable Long id) {
        TaskDTO task = taskService.findOne(id).orElseThrow(() ->
            new ObjectNotFoundError(messageSource.getMessage("error.task_not_found", null, locale))
        );
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

