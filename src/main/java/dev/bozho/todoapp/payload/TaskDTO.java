package dev.bozho.todoapp.payload;

import dev.bozho.todoapp.model.TaskPriority;
import lombok.Data;

@Data
public class TaskDTO {

    private Long taskId;
    private String title;
    private String description;
    private Boolean completed;
    private TaskPriority priority;

}
