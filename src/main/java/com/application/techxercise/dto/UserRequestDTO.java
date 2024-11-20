package com.application.techXercise.dto;

import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.utils.Role;

import java.util.List;
import java.util.Objects;

public class UserRequestDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Role role;
    private List<TaskEntity> tasksCreated;
    private List<TaskEntity> tasksExecuted;
    private List<CommentEntity> commentsCreated;

    public UserRequestDTO() {
    }

    public UserRequestDTO(Long id, String name, String surname, String email, String password, Role role, List<TaskEntity> tasksCreated, List<TaskEntity> tasksExecuted, List<CommentEntity> commentsCreated) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "UserDTO{" +
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
        UserRequestDTO userRequestDTO = (UserRequestDTO) o;
        return Objects.equals(id, userRequestDTO.id) && Objects.equals(name, userRequestDTO.name) && Objects.equals(surname, userRequestDTO.surname) && Objects.equals(email, userRequestDTO.email) && Objects.equals(password, userRequestDTO.password) && role == userRequestDTO.role && Objects.equals(tasksCreated, userRequestDTO.tasksCreated) && Objects.equals(tasksExecuted, userRequestDTO.tasksExecuted) && Objects.equals(commentsCreated, userRequestDTO.commentsCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, role, tasksCreated, tasksExecuted, commentsCreated);
    }
}
