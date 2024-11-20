package com.application.techXercise.entity;

import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

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
    private List<CommentEntity> commentEntities;

    public TaskEntity() {
    }

    public TaskEntity(String title, String description, TaskStatus status, TaskPriority priority, UserEntity author, UserEntity executor, List<CommentEntity> commentEntities) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.author = author;
        this.executor = executor;
        this.commentEntities = commentEntities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(UserEntity author) {
        this.author = author;
    }

    public UserEntity getExecutor() {
        return executor;
    }

    public void setExecutor(UserEntity executor) {
        this.executor = executor;
    }

    public List<CommentEntity> getComments() {
        return commentEntities;
    }

    public void setComments(List<CommentEntity> commentEntities) {
        this.commentEntities = commentEntities;
    }

    @Override
    public String toString() {
        return "Task{" +
                "executor=" + executor +
                ", author=" + author +
                ", priority=" + priority +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity taskEntity = (TaskEntity) o;
        return id == taskEntity.id && Objects.equals(title, taskEntity.title) && Objects.equals(description, taskEntity.description) && status == taskEntity.status && priority == taskEntity.priority && Objects.equals(author, taskEntity.author) && Objects.equals(executor, taskEntity.executor) && Objects.equals(commentEntities, taskEntity.commentEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, priority, author, executor, commentEntities);
    }
}
