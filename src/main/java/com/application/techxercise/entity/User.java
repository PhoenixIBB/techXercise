package com.application.techXercise.entity;

import com.application.techXercise.utils.Role;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "users")
public class User {

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
    private List<Task> tasksCreated;

    @OneToMany(mappedBy = "executor"
            , cascade = {CascadeType.ALL})
    private List<Task> tasksExecuted;

    @OneToMany(mappedBy = "commenter"
            , cascade = {CascadeType.ALL})
    private List<Comment> commentsCreated;

    public User() {
    }

    public User(long id, String name, String surname, String email, String password, Role role, List<Task> tasksCreated, List<Task> tasksExecuted, List<Comment> commentsCreated) {
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

    public List<Task> getTasksCreated() {
        return tasksCreated;
    }

    public void setTasksCreated(List<Task> tasksCreated) {
        this.tasksCreated = tasksCreated;
    }

    public List<Task> getTasksExecuted() {
        return tasksExecuted;
    }

    public void setTasksExecuted(List<Task> tasksExecuted) {
        this.tasksExecuted = tasksExecuted;
    }

    public List<Comment> getCommentsCreated() {
        return commentsCreated;
    }

    public void setCommentsCreated(List<Comment> commentsCreated) {
        this.commentsCreated = commentsCreated;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(tasksCreated, user.tasksCreated) && Objects.equals(tasksExecuted, user.tasksExecuted) && Objects.equals(commentsCreated, user.commentsCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, tasksCreated, tasksExecuted, commentsCreated);
    }

}

