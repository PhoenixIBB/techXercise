package com.application.techXercise.entity;

import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Название задачи не может быть пустым")
    @Size(max = 255, message = "Название задачи не должно превышать 255 символов")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Описание задачи не может быть пустым")
    @Size(max = 1000, message = "Описание задачи не должно превышать 1000 символов")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Статус задачи обязателен")
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @NotNull(message = "Приоритет задачи обязателен")
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @NotNull(message = "Автор задачи обязателен")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    @JsonBackReference("authorTasks")
    private UserEntity author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    @JsonBackReference("executorTasks")
    private UserEntity executor;

    @OneToMany(mappedBy = "commentedTaskEntity"
            , cascade = CascadeType.ALL)
    @JsonManagedReference("taskComments")
    private List<CommentEntity> commentsCreated;

}
