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

    public TaskEntity() {
    }

    public TaskEntity(Long id, String title, String description, TaskStatus status, TaskPriority priority, UserEntity author, UserEntity executor, List<CommentEntity> commentsCreated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.author = author;
        this.executor = executor;
        this.commentsCreated = commentsCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Название задачи не может быть пустым") @Size(max = 255, message = "Название задачи не должно превышать 255 символов") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Название задачи не может быть пустым") @Size(max = 255, message = "Название задачи не должно превышать 255 символов") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Описание задачи не может быть пустым") @Size(max = 1000, message = "Описание задачи не должно превышать 1000 символов") String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank(message = "Описание задачи не может быть пустым") @Size(max = 1000, message = "Описание задачи не должно превышать 1000 символов") String description) {
        this.description = description;
    }

    public @NotNull(message = "Статус задачи обязателен") TaskStatus getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "Статус задачи обязателен") TaskStatus status) {
        this.status = status;
    }

    public @NotNull(message = "Приоритет задачи обязателен") TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(@NotNull(message = "Приоритет задачи обязателен") TaskPriority priority) {
        this.priority = priority;
    }

    public @NotNull(message = "Автор задачи обязателен") UserEntity getAuthor() {
        return author;
    }

    public void setAuthor(@NotNull(message = "Автор задачи обязателен") UserEntity author) {
        this.author = author;
    }

    public UserEntity getExecutor() {
        return executor;
    }

    public void setExecutor(UserEntity executor) {
        this.executor = executor;
    }

    public List<CommentEntity> getCommentsCreated() {
        return commentsCreated;
    }

    public void setCommentsCreated(List<CommentEntity> commentsCreated) {
        this.commentsCreated = commentsCreated;
    }

    @Override
    public String toString() {
        return "TaskEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", author=" + author +
                ", executor=" + executor +
                ", commentsCreated=" + commentsCreated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return id == that.id && Objects.equals(title, that.title) && Objects.equals(description, that.description) && status == that.status && priority == that.priority && Objects.equals(author, that.author) && Objects.equals(executor, that.executor) && Objects.equals(commentsCreated, that.commentsCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, priority, author, executor, commentsCreated);
    }
}
