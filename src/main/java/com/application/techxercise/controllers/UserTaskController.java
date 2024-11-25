package com.application.techXercise.controllers;

import com.application.techXercise.dto.TaskResponseDTO;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.services.TaskService;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Получить задачи по исполнителю", description = "Метод возвращает задачи, которые назначены пользователю-исполнителю.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи успешно получены"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
    })
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

    @Operation(summary = "Получить задачи по автору", description = "Метод возвращает задачи, созданные пользователем-автором.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи успешно получены"),
            @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
    })
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

    @Operation(summary = "Получить задачу по ID", description = "Метод возвращает информацию о задаче по её идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно получена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable long taskId) throws TaskNotFoundException {
        TaskResponseDTO taskEntity = taskService.getTaskById(taskId);
        return ResponseEntity.ok(taskEntity);
    }

    @Operation(summary = "Обновить статус задачи", description = "Метод позволяет обновить статус задачи, если пользователь является её исполнителем.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @PatchMapping("/{taskId}/")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(@PathVariable long taskId
            , @Valid @RequestParam TaskStatus status) throws TaskNotFoundException {
        TaskResponseDTO updatedTaskEntity = taskService.updateTaskStatusByUser(taskId, status);
        return updatedTaskEntity != null ?
                ResponseEntity.ok(updatedTaskEntity) :
                ResponseEntity.notFound().build();
    }

}
