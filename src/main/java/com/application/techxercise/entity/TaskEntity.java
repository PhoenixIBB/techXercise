package com.application.techXercise.entity;

import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TaskPriority priority;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id")
    private UserEntity executor;

    @OneToMany(mappedBy = "commentedTaskEntity"
            , cascade = CascadeType.ALL)
    private List<CommentEntity> commentEntities;

    public TaskEntity() {
    }

    public TaskEntity(int id, String title, String description, TaskStatus status, TaskPriority priority, UserEntity author, UserEntity executor, List<CommentEntity> commentEntities) {
        this.id = id;
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
