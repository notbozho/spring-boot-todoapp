package dev.bozho.todoapp.controller;

import dev.bozho.todoapp.exception.TaskException;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.payload.TaskDTO;
import dev.bozho.todoapp.repository.TaskRepository;
import dev.bozho.todoapp.service.impl.TaskService;
import dev.bozho.todoapp.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    
    private final UserService userService;

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    @PostMapping("add")
    public ResponseEntity<TaskDTO> addTask(@Valid @RequestBody TaskDTO task) throws TaskException {
        TaskDTO createdTaskDTO =  taskService.addTask(task);

        return new ResponseEntity<TaskDTO>(createdTaskDTO, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO task) throws TaskException {
//        TaskDTO updatedTaskDTO = taskService.updateTask(task.getTaskId(), task);

        throw new NotYetImplementedException();
//        return new ResponseEntity<TaskDTO>(updatedTaskDTO, HttpStatus.CREATED);
    }

    @GetMapping("{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) throws TaskException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<TaskDTO>(modelMapper.map(user, TaskDTO.class), HttpStatus.OK);
    }

}
