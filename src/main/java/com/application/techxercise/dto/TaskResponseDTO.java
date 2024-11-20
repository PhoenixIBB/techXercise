package com.application.techXercise.dto;

import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;

import java.util.List;
import java.util.Objects;

public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UserEntity author;
    private UserEntity executor;
    private List<CommentEntity> comments;

    public TaskResponseDTO() {
    }

    public TaskResponseDTO(Long id, String title, String description, TaskStatus status, TaskPriority priority, UserEntity author, UserEntity executor, List<CommentEntity> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.author = author;
        this.executor = executor;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "TaskRequestDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", author=" + author +
                ", executor=" + executor +
                ", comments=" + comments +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskResponseDTO that = (TaskResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && status == that.status && priority == that.priority && Objects.equals(author, that.author) && Objects.equals(executor, that.executor) && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, status, priority, author, executor, comments);
    }
}
