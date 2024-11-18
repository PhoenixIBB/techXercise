package com.application.techXercise.controllers;

import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.services.TaskService;
import com.application.techXercise.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/tasks/{userId}")
public class UserTaskController {

    private final TaskService taskService;

    public UserTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Получить все задачи пользователя
//    @GetMapping("/")
//    public ResponseEntity<List<TaskEntity>> showAllTasks() {
//        List<TaskEntity> taskEntities = taskService.getAllTasks();
//        return taskEntities.isEmpty() ?
//                ResponseEntity.noContent().build() :
//                ResponseEntity.ok(taskEntities);
//    }

    // Эндпоинт для вывода задач исполнителя
    @GetMapping("/executor")
    public ResponseEntity<List<TaskEntity>> showExecutorTasks(@PathVariable long userId) {
        List<TaskEntity> taskEntities = taskService.getTasksByExecutor(userId);
        return taskEntities.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(taskEntities);
    }

    // Эндпоинт для вывода задач, автором которых является пользователь. ОДНАКО. Он не может создавать задачи. В угоду
    // ТЗ (а там речь шла об "авторах") оставим, вдруг нужно будет добавить функционал создания простым юзерам
    @GetMapping("/author")
    public ResponseEntity<List<TaskEntity>> showAuthorTasks(@PathVariable long userId) {
        List<TaskEntity> taskEntities = taskService.getTasksByAuthor(userId);
        return taskEntities.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(taskEntities);
    }

    // Получить конкретную задачу
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable long taskId) throws TaskNotFoundException {
        TaskEntity taskEntity = taskService.getTaskById(taskId);
        return taskEntity != null ?
                ResponseEntity.ok(taskEntity) :
                ResponseEntity.notFound().build();
    }

    // Редактирование объектов
    @PatchMapping("/{taskId}/")
    public ResponseEntity<TaskEntity> updateTaskStatus(@PathVariable long taskId, @RequestParam TaskStatus status) throws TaskNotFoundException {
        if (status == null) {
            return ResponseEntity.badRequest().body(null);
        }

        TaskEntity updatedTaskEntity = taskService.updateTaskStatusByUser(taskId, status);
        return updatedTaskEntity != null ?
                ResponseEntity.ok(updatedTaskEntity) :
                ResponseEntity.notFound().build();
    }

}
