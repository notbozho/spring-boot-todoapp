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
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO register(User user) throws UserException {
        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            throw new UserException("User already exists");
        }

        User registeredUser = userRepository.save(user);

        return modelMapper.map(registeredUser, UserDTO.class);
    }

    @Override
    public UserDTO login(User user) {
        throw new NotYetImplementedException();
    }

    @Override
    public UserDTO getUser(String email) throws UserException {
        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (!userOptional.isPresent()) {
            throw new UserException("User does not exist");
        }

        User user = userOptional.get();

        return modelMapper.map(user, UserDTO.class);
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
