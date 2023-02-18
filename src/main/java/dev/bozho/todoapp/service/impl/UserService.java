package dev.bozho.todoapp.service.impl;

import dev.bozho.todoapp.exception.TokenException;
import dev.bozho.todoapp.exception.UserException;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.payload.TaskDTO;
import dev.bozho.todoapp.payload.UserDTO;
import dev.bozho.todoapp.repository.UserRepository;
import dev.bozho.todoapp.service.IUserService;
import dev.bozho.todoapp.service.tokens.EmailTokenService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    private final EmailTokenService emailTokenService;

    private final EmailService emailService;

    private final ModelMapper modelMapper;

    @Override
    public UserDTO getUser(String email) throws UserException {
        User user = userRepository.findUserByEmail(email)
                                  .orElseThrow(() -> new UserException("User not found"));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
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
    public List<TaskDTO> getTasks() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<TaskDTO> tasks = user.getTasks().stream()
                                  .map(task -> modelMapper.map(task, TaskDTO.class))
                                  .toList();

        return tasks;
    }

    @Override
    public void resendConfirmationEmail(User user) throws TokenException {
        String token = emailTokenService.generateToken(user);

        emailService.sendEmailConfirmation(
                user.getEmail(),
                token
        );
    }
}
