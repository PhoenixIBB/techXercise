package com.application.techXercise.dto;


import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.entity.UserEntity;

import java.time.LocalDate;
import java.util.Objects;

public class CommentResponseDTO {

    private Long id;
    private String content;
    private UserEntity commenter;
    private TaskEntity commentedTaskEntity;
    private LocalDate commentCreationDate;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String content, UserEntity commenter, TaskEntity commentedTaskEntity, LocalDate commentCreationDate) {
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

    public TaskEntity getCommentedTaskEntity() {
        return commentedTaskEntity;
    }

    public void setCommentedTaskEntity(TaskEntity commentedTaskEntity) {
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
        return "CommentRequestDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", commenter=" + commenter +
                ", commentedTaskEntity=" + commentedTaskEntity +
                ", commentCreationDate=" + commentCreationDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentResponseDTO that = (CommentResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(commenter, that.commenter) && Objects.equals(commentedTaskEntity, that.commentedTaskEntity) && Objects.equals(commentCreationDate, that.commentCreationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, commenter, commentedTaskEntity, commentCreationDate);
    }
}
