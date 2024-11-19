package com.application.techXercise.controllers;

import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.services.TaskService;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminTaskController {

    private final TaskService taskService;

    @Autowired
    public AdminTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Эндпоинт создания задачи
    @PostMapping("/")
    public ResponseEntity<TaskEntity> createTask(@Valid @RequestBody TaskEntity taskEntity) throws UserNotFoundException {
        TaskEntity createdTaskEntity = taskService.createTask(taskEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskEntity);
    }

    // Эндпоинт вывода всех задач
    @GetMapping("/tasks")
    public ResponseEntity<List<TaskEntity>> showAllTasks() {
        List<TaskEntity> taskEntities = taskService.getAllTasks();
        return ResponseEntity.ok(taskEntities);
    }

    // Эндпоинт для вывода задач исполнителя
    @GetMapping("/by-executor/{executorId}")
    public ResponseEntity<Page<TaskEntity>> getTasksByExecutor(
            @PathVariable Long executorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority) {

        Page<TaskEntity> tasks = taskService.getTasksByExecutor(executorId, page, size, status, priority);
        return ResponseEntity.ok(tasks);
    }

    // Эндпоинт для вывода задач автора
    @GetMapping("/by-author/{authorId}")
    public ResponseEntity<Page<TaskEntity>> getTasksByAuthor(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority) {

        Page<TaskEntity> tasks = taskService.getTasksByAuthor(authorId, page, size, status, priority);
        return ResponseEntity.ok(tasks);
    }

    // Эндпоинт вывода конкретной задачи по ID
    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable long taskId) throws TaskNotFoundException {
        TaskEntity taskEntity = taskService.getTaskById(taskId);
        return taskEntity != null ?
                ResponseEntity.ok(taskEntity) :
                ResponseEntity.notFound().build();
    }

    // Эндпоинт редактирования задачи
    @PutMapping("/")
    public ResponseEntity<TaskEntity> updateTask(@Valid @RequestBody TaskEntity taskEntity) throws TaskNotFoundException {
        return ResponseEntity.ok(taskService.updateTaskByAdmin(taskEntity));
    }

    // Эндпоинт удаления задачи
    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable long taskId) throws TaskNotFoundException {
        boolean isDeleted = taskService.deleteTask(taskId);
        return isDeleted ?
                ResponseEntity.ok("Задача удалена успешно.") :
                ResponseEntity.notFound().build();
    }


}
