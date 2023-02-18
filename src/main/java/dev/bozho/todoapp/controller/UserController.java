package dev.bozho.todoapp.controller;

import dev.bozho.todoapp.exception.TaskException;
import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.payload.TaskDTO;
import dev.bozho.todoapp.payload.UserDTO;
import dev.bozho.todoapp.repository.UserRepository;
import dev.bozho.todoapp.service.impl.EmailService;
import dev.bozho.todoapp.service.impl.UserService;
import dev.bozho.todoapp.service.tokens.EmailTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final EmailTokenService emailTokenService;

    private final EmailService emailService;

    @PutMapping("")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody User user) throws UserException {
        UserDTO updatedUserDTO = userService.updateUser(user);

        return new ResponseEntity<UserDTO>(updatedUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDTO> getUser() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new ResponseEntity<UserDTO>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(path = "tasks")
    public ResponseEntity<List<TaskDTO>> getTasks() throws TaskException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<TaskDTO> tasks = userService.getTasks();

        return new ResponseEntity<List<TaskDTO>>(tasks, HttpStatus.OK);
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) throws TokenException {
        String response = emailTokenService.confirmToken(token);

        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "resendConfirmation")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> resendConfirmation() throws TokenException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.resendConfirmationEmail(user);

        return ResponseEntity.ok("Email resent.");
    }

}
