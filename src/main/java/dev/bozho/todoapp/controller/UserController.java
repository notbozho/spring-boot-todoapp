package dev.bozho.todoapp.controller;

import dev.bozho.todoapp.exception.TaskException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.payload.TaskDTO;
import dev.bozho.todoapp.payload.UserDTO;
import dev.bozho.todoapp.repository.UserRepository;
import dev.bozho.todoapp.service.impl.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    public UserController(UserService userService, UserRepository userRepository, ModelMapper modelMapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PutMapping("/")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody User user) throws UserException {
        UserDTO updatedUserDTO = userService.updateUser(user);

        return new ResponseEntity<UserDTO>(updatedUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/info")
    public ResponseEntity<UserDTO> getUser() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findUserByEmail(email).get();

        return new ResponseEntity<UserDTO>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();

        return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTasks() throws TaskException {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<TaskDTO> tasks = userService.getTasks(email);

        return new ResponseEntity<List<TaskDTO>>(tasks, HttpStatus.OK);
    }

}
