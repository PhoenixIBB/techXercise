package com.application.techXercise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Комментарий не может быть пустым")
    @Size(max = 1000, message = "Длина комментария не должна превышать 1000 символов")
    @Column(name = "content")
    private String content;

    @NotNull(message = "Невозможно создать комментарий без авторства")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commenter_id")
    @JsonBackReference("commenterComments")
    private UserEntity commenter;

    @NotNull(message = "Комментарий не существует без задачи, к которой он был написан")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commented_task_id")
    @JsonBackReference("taskComments")
    private TaskEntity commentedTaskEntity;

    @Column(name = "comment_creation_date")
    private LocalDate commentCreationDate;

    @PrePersist
    public void prePersist() {
        if (this.commentCreationDate == null) {
            this.commentCreationDate = LocalDate.now();
        }
    }

    public CommentEntity() {
    }

    public CommentEntity(String content, UserEntity commenter, TaskEntity commentedTaskEntity, LocalDate commentCreationDate) {
        this.content = content;
        this.commenter = commenter;
        this.commentedTaskEntity = commentedTaskEntity;
        this.commentCreationDate = commentCreationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserEntity getCommenter() {
        return commenter;
    }

    public void setCommenter(UserEntity commenter) {
        this.commenter = commenter;
    }

    public TaskEntity getCommentedTask() {
        return commentedTaskEntity;
    }

    public void setCommentedTask(TaskEntity commentedTaskEntity) {
        this.commentedTaskEntity = commentedTaskEntity;
    }

    public LocalDate getCommentCreationDate() {
        return commentCreationDate;
    }

    public void setCommentCreationDate(LocalDate commentCreationDate) {
        this.commentCreationDate = commentCreationDate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentCreationDate=" + commentCreationDate +
                ", commentedTask=" + commentedTaskEntity +
                ", commenter=" + commenter +
                ", content='" + content + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity commentEntity = (CommentEntity) o;
        return id == commentEntity.id && Objects.equals(content, commentEntity.content) && Objects.equals(commenter, commentEntity.commenter) && Objects.equals(commentedTaskEntity, commentEntity.commentedTaskEntity) && Objects.equals(commentCreationDate, commentEntity.commentCreationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, commenter, commentedTaskEntity, commentCreationDate);
    }
}
