package com.application.techXercise.controllers;

import com.application.techXercise.dto.TaskResponseDTO;
import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.services.TaskService;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/tasks/{userId}")
public class UserTaskController {

    private final TaskService taskService;

    @Autowired
    public UserTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Эндпоинт для получения задач исполнителя
    @GetMapping("/executor")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByExecutor(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority) {

        Page<TaskResponseDTO> tasks = taskService.getTasksByExecutor(userId, page, size, status, priority);
        return ResponseEntity.ok(tasks);
    }


    // Эндпоин для получения задач по автору (хотя автор всегда админ)
    @GetMapping("/author")
    public ResponseEntity<Page<TaskResponseDTO>> getTasksByAuthor(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority) {

        Page<TaskResponseDTO> tasks = taskService.getTasksByAuthor(userId, page, size, status, priority);
        return ResponseEntity.ok(tasks);
    }

    // Получить конкретную задачу
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable long taskId) throws TaskNotFoundException {
        TaskResponseDTO taskEntity = taskService.getTaskById(taskId);
        return ResponseEntity.ok(taskEntity);
    }

    // Редактирование объектов
    @PatchMapping("/{taskId}/")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(@PathVariable long taskId, @Valid @RequestParam TaskStatus status) throws TaskNotFoundException {
        TaskResponseDTO updatedTaskEntity = taskService.updateTaskStatusByUser(taskId, status);
        return updatedTaskEntity != null ?
                ResponseEntity.ok(updatedTaskEntity) :
                ResponseEntity.notFound().build();
    }

}
