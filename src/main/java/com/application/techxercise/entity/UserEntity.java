package com.application.techXercise.entity;

import com.application.techXercise.utils.Role;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "author"
            , cascade = {CascadeType.ALL})
    private List<TaskEntity> tasksCreated;

    @OneToMany(mappedBy = "executor"
            , cascade = {CascadeType.ALL})
    private List<TaskEntity> tasksExecuted;

    @OneToMany(mappedBy = "commenter"
            , cascade = {CascadeType.ALL})
    private List<CommentEntity> commentsCreated;

    public UserEntity() {
    }

    public UserEntity(String name, String surname, String email, String password, Role role, List<TaskEntity> tasksCreated, List<TaskEntity> tasksExecuted, List<CommentEntity> commentsCreated) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public String getRolesString() {
        return role.toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "role=" + role +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return id == userEntity.id && Objects.equals(name, userEntity.name) && Objects.equals(surname, userEntity.surname) && Objects.equals(email, userEntity.email) && Objects.equals(password, userEntity.password) && Objects.equals(tasksCreated, userEntity.tasksCreated) && Objects.equals(tasksExecuted, userEntity.tasksExecuted) && Objects.equals(commentsCreated, userEntity.commentsCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, tasksCreated, tasksExecuted, commentsCreated);
    }

}

