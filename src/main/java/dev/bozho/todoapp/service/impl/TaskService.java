package dev.bozho.todoapp.service.impl;

import dev.bozho.todoapp.exception.TaskException;
import dev.bozho.todoapp.model.Task;
import dev.bozho.todoapp.model.User;
import dev.bozho.todoapp.payload.TaskDTO;
import dev.bozho.todoapp.repository.TaskRepository;
import dev.bozho.todoapp.repository.UserRepository;
import dev.bozho.todoapp.service.ITaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaskDTO addTask(TaskDTO taskDTO) throws TaskException {

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findUserByEmail(email).get();

        Task task = modelMapper.map(taskDTO, Task.class);

        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return modelMapper.map(savedTask, TaskDTO.class);
    }

    @Override
    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO) throws TaskException {
        Task task = taskRepository.findById(taskId)
                                  .orElseThrow(() -> new TaskException("Task does not exist"));

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!task.getUser().getEmail().equals(email)) {
            throw new TaskException("You do not have any task with id " + taskId + " to update");
        }

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.getCompleted());
        task.setPriority(taskDTO.getPriority());

        task = taskRepository.save(task);

        return modelMapper.map(task, TaskDTO.class);
    }

    @Override
    public String deleteTask(Long taskId) throws TaskException {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskException("Task does not exist"));

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!task.getUser().getEmail().equals(email)) {
            throw new TaskException("You do not have any task with id " + taskId + " to delete");
        }

        taskRepository.delete(task);

        return "Task with id " + taskId + " deleted successfully";
    }
}
