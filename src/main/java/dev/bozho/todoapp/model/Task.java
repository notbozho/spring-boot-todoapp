package dev.bozho.todoapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    private String title;
    private String description;

    @ColumnDefault("false")
    private Boolean completed = false;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @ManyToOne
    private User user;
}
