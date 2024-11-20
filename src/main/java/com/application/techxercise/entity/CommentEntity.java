package com.application.techXercise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long id;

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

    public CommentEntity(long id, String content, UserEntity commenter, TaskEntity commentedTaskEntity, LocalDate commentCreationDate) {
        this.id = id;
        this.content = content;
        this.commenter = commenter;
        this.commentedTaskEntity = commentedTaskEntity;
        this.commentCreationDate = commentCreationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Комментарий не может быть пустым") @Size(max = 1000, message = "Длина комментария не должна превышать 1000 символов") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Комментарий не может быть пустым") @Size(max = 1000, message = "Длина комментария не должна превышать 1000 символов") String content) {
        this.content = content;
    }

    public @NotNull(message = "Невозможно создать комментарий без авторства") UserEntity getCommenter() {
        return commenter;
    }

    public void setCommenter(@NotNull(message = "Невозможно создать комментарий без авторства") UserEntity commenter) {
        this.commenter = commenter;
    }

    public @NotNull(message = "Комментарий не существует без задачи, к которой он был написан") TaskEntity getCommentedTaskEntity() {
        return commentedTaskEntity;
    }

    public void setCommentedTaskEntity(@NotNull(message = "Комментарий не существует без задачи, к которой он был написан") TaskEntity commentedTaskEntity) {
        this.commentedTaskEntity = commentedTaskEntity;
    }

    public LocalDate getCommentCreationDate() {
        return commentCreationDate;
    }

    public void setCommentCreationDate(LocalDate commentCreationDate) {
        this.commentCreationDate = commentCreationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentEntity that = (CommentEntity) o;
        return id == that.id && Objects.equals(content, that.content) && Objects.equals(commenter, that.commenter) && Objects.equals(commentedTaskEntity, that.commentedTaskEntity) && Objects.equals(commentCreationDate, that.commentCreationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, commenter, commentedTaskEntity, commentCreationDate);
    }
}
