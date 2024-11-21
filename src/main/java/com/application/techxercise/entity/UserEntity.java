package com.application.techXercise.entity;

import com.application.techXercise.utils.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Поле 'имя' не может быть пустым")
    @Size(max = 55, message = "Длина имени не должна превышать 55 символов")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Поле 'фамилия' не может быть пустым")
    @Size(max = 55, message = "Длина фамилии не должна превышать 55 символов")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "Поле 'email' не может быть пустым")
    @Size(max = 55, message = "Длина адреса электронной почты не должна превышать 55 символов")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[a-zA-Z]{2,6}$",
            message = "Неверный формат email")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль должен содержать минимум 8 символов")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}",
            message = "Пароль должен содержать хотя бы одну цифру, одну заглавную букву и один специальный символ")
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "author"
            , cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("authorTasks")
    private List<TaskEntity> tasksCreated;

    @OneToMany(mappedBy = "executor"
            , cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JsonManagedReference("executorTasks")
    private List<TaskEntity> tasksExecuted;

    @OneToMany(mappedBy = "commenter"
            , cascade = {CascadeType.ALL})
    @JsonManagedReference("commenterComments")
    private List<CommentEntity> commentsCreated;

    public String getRolesString() {
        return role != null ? role.toString() : "USER_ROLE";
    }

}

