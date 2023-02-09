package dev.bozho.todoapp.controller;

import dev.bozho.todoapp.exception.TaskException;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.payload.TaskDTO;
import dev.bozho.todoapp.repository.TaskRepository;
import dev.bozho.todoapp.service.impl.TaskService;
import dev.bozho.todoapp.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@AllArgsConstructor
public class TaskController {

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping("/")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO task) throws TaskException {
        TaskDTO updatedTaskDTO = taskService.updateTask(task.getTaskId(), task);

        return new ResponseEntity<TaskDTO>(updatedTaskDTO, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<TaskDTO> getTask() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<TaskDTO>(modelMapper.map(user, TaskDTO.class), HttpStatus.OK);
    }

}
