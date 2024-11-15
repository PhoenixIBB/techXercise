package com.application.techXercise.controllers;

import com.application.techXercise.entity.Comment;
import com.application.techXercise.entity.Task;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.services.CommentService;
import com.application.techXercise.services.TaskService;
import com.application.techXercise.services.UserService;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    // Базовые операции

    @GetMapping("/tasks")
    public List<Task> showAllTasks() {
        return taskService.getAllTasks();
    }

    // Пара выводов словарей на случай, если не успею сделать view

    @GetMapping("/statuses")
    public List<String> getAllStatuses() {
        return Arrays.stream(TaskStatus.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/priorities")
    public List<String> getAllPriorities() {
        return Arrays.stream(TaskPriority.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable long taskId) throws TaskNotFoundException {
        Task task = taskService.getTaskById(taskId);

        if (task != null) {
            return ResponseEntity.ok(task);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable long taskId) {
        boolean isDeleted = taskService.deleteTask(taskId);
        if (isDeleted) {
            return ResponseEntity.ok("Записб удалена успешно.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Редактирование объектов

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable long taskId, @RequestParam TaskStatus status) throws TaskNotFoundException {
        Task updatedTask = taskService.updateTaskStatus(taskId, status);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{taskId}/priority")
    public ResponseEntity<Task> updateTaskPriority(@PathVariable long taskId, @RequestParam TaskPriority priority) throws TaskNotFoundException {
        Task updatedTask = taskService.updateTaskPriority(taskId, priority);

        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{taskId}/assignee")
    public ResponseEntity<Task> updateTaskExecutor(@PathVariable long taskId, @RequestParam long userId) throws TaskNotFoundException {
        Task updatedTask = taskService.updateTaskExecutor(taskId, userId);

        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
