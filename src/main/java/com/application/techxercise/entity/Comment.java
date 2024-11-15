package com.application.techXercise.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "commenter_id")
    private User commenter;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "commentedTask_id")
    private Task commentedTask;

    @Column(name = "commentCreationDate")
    private LocalDate commentCreationDate;

    @PrePersist
    public void prePersist() {
        if (this.commentCreationDate == null) {
            this.commentCreationDate = LocalDate.now();
        }
    }

    public Comment() {
    }

    public Comment(long id, String content, User commenter, Task commentedTask, LocalDate commentCreationDate) {
        this.id = id;
        this.content = content;
        this.commenter = commenter;
        this.commentedTask = commentedTask;
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

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public Task getCommentedTask() {
        return commentedTask;
    }

    public void setCommentedTask(Task commentedTask) {
        this.commentedTask = commentedTask;
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
                ", commentedTask=" + commentedTask +
                ", commenter=" + commenter +
                ", content='" + content + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id && Objects.equals(content, comment.content) && Objects.equals(commenter, comment.commenter) && Objects.equals(commentedTask, comment.commentedTask) && Objects.equals(commentCreationDate, comment.commentCreationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, commenter, commentedTask, commentCreationDate);
    }
}
