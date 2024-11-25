package com.application.techXercise.entity;

import com.application.techXercise.utils.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
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

    public UserEntity() {
    }

    public UserEntity(Long id, String name, String surname, String email, String password, Role role, List<TaskEntity> tasksCreated, List<TaskEntity> tasksExecuted, List<CommentEntity> commentsCreated) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.tasksCreated = tasksCreated;
        this.tasksExecuted = tasksExecuted;
        this.commentsCreated = commentsCreated;
    }

    public String getRolesString() {
        return role != null ? role.toString() : "USER";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Поле 'имя' не может быть пустым") @Size(max = 55, message = "Длина имени не должна превышать 55 символов") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Поле 'имя' не может быть пустым") @Size(max = 55, message = "Длина имени не должна превышать 55 символов") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Поле 'фамилия' не может быть пустым") @Size(max = 55, message = "Длина фамилии не должна превышать 55 символов") String getSurname() {
        return surname;
    }

    public void setSurname(@NotBlank(message = "Поле 'фамилия' не может быть пустым") @Size(max = 55, message = "Длина фамилии не должна превышать 55 символов") String surname) {
        this.surname = surname;
    }

    public @NotBlank(message = "Поле 'email' не может быть пустым") @Size(max = 55, message = "Длина адреса электронной почты не должна превышать 55 символов") @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[a-zA-Z]{2,6}$",
            message = "Неверный формат email") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Поле 'email' не может быть пустым") @Size(max = 55, message = "Длина адреса электронной почты не должна превышать 55 символов") @Pattern(regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+)\\.[a-zA-Z]{2,6}$",
            message = "Неверный формат email") String email) {
        this.email = email;
    }

    public @NotNull(message = "Пароль не может быть пустым") @Size(min = 8, message = "Пароль должен содержать минимум 8 символов") @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}",
            message = "Пароль должен содержать хотя бы одну цифру, одну заглавную букву и один специальный символ") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Пароль не может быть пустым") @Size(min = 8, message = "Пароль должен содержать минимум 8 символов") @Pattern(regexp = "(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}",
            message = "Пароль должен содержать хотя бы одну цифру, одну заглавную букву и один специальный символ") String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<TaskEntity> getTasksCreated() {
        return tasksCreated;
    }

    public void setTasksCreated(List<TaskEntity> tasksCreated) {
        this.tasksCreated = tasksCreated;
    }

    public List<TaskEntity> getTasksExecuted() {
        return tasksExecuted;
    }

    public void setTasksExecuted(List<TaskEntity> tasksExecuted) {
        this.tasksExecuted = tasksExecuted;
    }

    public List<CommentEntity> getCommentsCreated() {
        return commentsCreated;
    }

    public void setCommentsCreated(List<CommentEntity> commentsCreated) {
        this.commentsCreated = commentsCreated;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", tasksCreated=" + tasksCreated +
                ", tasksExecuted=" + tasksExecuted +
                ", commentsCreated=" + commentsCreated +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && role == that.role && Objects.equals(tasksCreated, that.tasksCreated) && Objects.equals(tasksExecuted, that.tasksExecuted) && Objects.equals(commentsCreated, that.commentsCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, role, tasksCreated, tasksExecuted, commentsCreated);
    }
}

