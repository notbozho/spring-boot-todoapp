package dev.bozho.todoapp.service;

import dev.bozho.todoapp.exception.TaskException;
import dev.bozho.todoapp.payload.TaskDTO;

public interface ITaskService {

    TaskDTO addTask(TaskDTO taskDTO) throws TaskException;

    TaskDTO updateTask(Long taskId, TaskDTO taskDTO) throws TaskException;

    String deleteTask(Long taskId) throws TaskException;
}
