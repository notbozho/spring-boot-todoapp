package dev.bozho.todoapp.service;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.payload.TaskDTO;
import dev.bozho.todoapp.payload.UserDTO;

import java.util.List;

public interface IUserService {

    UserDTO getUser(String email) throws UserException;

    List<User> getAllUsers();

    UserDTO updateUser(User user);

    void deleteUser(String email);

    List<TaskDTO> getTasks(String email);

    void resendConfirmationEmail(User user) throws TokenException;

}
