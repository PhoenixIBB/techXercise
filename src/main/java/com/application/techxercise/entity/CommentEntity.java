package com.application.techXercise.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@RequiredArgsConstructor
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

}
