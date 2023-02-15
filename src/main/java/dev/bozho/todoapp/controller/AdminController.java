package dev.bozho.todoapp.controller;

import dev.bozho.todoapp.model.Task;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.service.impl.TaskService;
import dev.bozho.todoapp.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping(path = "all/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "all/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();

        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

}
