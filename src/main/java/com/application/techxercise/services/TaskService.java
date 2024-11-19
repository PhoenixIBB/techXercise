package com.application.techXercise.services;

import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.repositories.TaskRepository;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.utils.TaskStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Создать задачу
    public TaskEntity createTask(TaskEntity taskEntity) throws UserNotFoundException {

        UserEntity author = userRepository.findById(taskEntity.getAuthor().getId())
                .orElseThrow(() -> new UserNotFoundException("Author not found"));
        UserEntity executor = userRepository.findById(taskEntity.getExecutor().getId())
                .orElseThrow(() -> new UserNotFoundException("Executor not found"));

        taskEntity.setAuthor(author);
        taskEntity.setExecutor(executor);

        taskRepository.saveAndFlush(taskEntity);
        return taskEntity;
    }

    // Получить все задачи
    public List<TaskEntity> getAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        if (taskEntities.isEmpty()) {
            return new ArrayList<>();
        }
        return taskEntities;
    }

    // Получить задачу по ID
    public TaskEntity getTaskById(long id) throws TaskNotFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена!"));
    }

    // Получить задачи конкретного автора
    public List<TaskEntity> getTasksByAuthor(long userId) {
        return taskRepository.findByAuthorId(userId);
    }

    // Получить задачи конкретного автора
    public List<TaskEntity> getTasksByExecutor(long userId) {
        return taskRepository.findByExecutorId(userId);
    }

    // Редактирование задачи для администратора
    public TaskEntity updateTaskByAdmin(TaskEntity updatedTaskEntity) throws TaskNotFoundException {
        TaskEntity existingTaskEntity = taskRepository.findById(updatedTaskEntity.getId())
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена!"));

        if (!updatedTaskEntity.getTitle().equals(existingTaskEntity.getTitle())) {
            existingTaskEntity.setTitle(updatedTaskEntity.getTitle());
        }
        if (!updatedTaskEntity.getDescription().equals(existingTaskEntity.getDescription())) {
            existingTaskEntity.setDescription(updatedTaskEntity.getDescription());
        }
        if (updatedTaskEntity.getStatus() != null) {
            existingTaskEntity.setStatus(updatedTaskEntity.getStatus());
        }
        if (updatedTaskEntity.getPriority() != null) {
            existingTaskEntity.setPriority(updatedTaskEntity.getPriority());
        }
        if (updatedTaskEntity.getExecutor() != null) {
            existingTaskEntity.setExecutor(updatedTaskEntity.getExecutor());
        }
        // Добавлять поля к изменению — сюда
        return taskRepository.saveAndFlush(existingTaskEntity);
    }

    // Универсальный Update-метод на случай, если пользователю позволят изменять другие поля
    public TaskEntity updateTaskPropertyByUser(long id, Consumer<TaskEntity> updater) throws TaskNotFoundException {
        TaskEntity taskEntityForUpdating = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Задача не найдена."));
        updater.accept(taskEntityForUpdating);
        return taskRepository.saveAndFlush(taskEntityForUpdating);
    }

    // Смена статуса задачи пользователем
    public TaskEntity updateTaskStatusByUser(long id, TaskStatus status) throws TaskNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity currentUser = userRepository.findByEmail(email);
        long currentUserId = currentUser.getId();

        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена."));

        if (taskEntity.getExecutor() == null || taskEntity.getExecutor().getId() != currentUserId) {
            throw new SecurityException("Вы не являетесь исполнителем этой задачи и не можете менять её статус.");
        }

        return updateTaskPropertyByUser(id, task -> task.setStatus(status));
    }

    // Удаление задачу
    public boolean deleteTask(long id) throws TaskNotFoundException {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Задача с id " + id + " не найдена.");
        }
        taskRepository.deleteById(id);
        return true;
    }

}
