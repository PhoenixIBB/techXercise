package com.application.techXercise.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id")
    private UserEntity commenter;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "commentedTask_id")
    private TaskEntity commentedTaskEntity;

    @Column(name = "commentCreationDate")
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
