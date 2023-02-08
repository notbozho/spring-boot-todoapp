package dev.bozho.todoapp.service.impl;

import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.payload.TaskDTO;
import dev.bozho.todoapp.payload.UserDTO;
import dev.bozho.todoapp.repository.TaskRepository;
import dev.bozho.todoapp.repository.UserRepository;
import dev.bozho.todoapp.service.IUserService;
import org.hibernate.cfg.NotYetImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO getUser(String email) throws UserException {
        User user = userRepository.findUserByEmail(email)
                                  .orElseThrow(() -> new UserException("User not found"));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                             .stream()
                             .map(user -> modelMapper.map(user, UserDTO.class))
                             .toList();
    }

    @Override
    public UserDTO updateUser(User user) {
        throw new NotYetImplementedException();
    }

    @Override
    public void deleteUser(String email) {
        throw new NotYetImplementedException();
    }

    @Override
    public List<TaskDTO> getTasks(String email) {
        return null;
    }
}
