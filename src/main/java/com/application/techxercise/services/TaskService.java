package com.application.techXercise.services;

import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.repositories.TaskRepository;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.dto.TaskRequestDTO;
import com.application.techXercise.dto.TaskResponseDTO;
import com.application.techXercise.utils.TaskMapper;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskSpecifications;
import com.application.techXercise.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    // Создать задачу
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) throws UserNotFoundException {

        Long authorId = taskRequestDTO.getAuthor().getId();
        Long executorId = taskRequestDTO.getExecutor().getId();

        if (authorId == null || executorId == null ||
                userRepository.findById(authorId).isEmpty() ||
                userRepository.findById(executorId).isEmpty()) {
            throw new UserNotFoundException("Пользователь с указанным id не найден.");
        }

        TaskEntity taskEntity = taskMapper.fromRequestDTO(taskRequestDTO);
        taskRepository.saveAndFlush(taskEntity);

        return taskMapper.toResponseDTO(taskEntity);
    }

    // Получить все задачи
    public List<TaskResponseDTO> getAllTasks() {
        List<TaskEntity> allTasks = taskRepository.findAll();
        if (allTasks.isEmpty()) {
            return new ArrayList<>();
        }
        return allTasks.stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Получить задачу по ID
    public TaskResponseDTO getTaskById(long id) throws TaskNotFoundException {
        return taskMapper.toResponseDTO(taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена!")));
    }

    // Получить задачи конкретного автора
    public Page<TaskResponseDTO> getTasksByAuthor(long authorId, int page, int size, TaskStatus status, TaskPriority priority) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<TaskEntity> specification = Specification.where(TaskSpecifications.byAuthor(authorId))
                .and(TaskSpecifications.byStatus(status))
                .and(TaskSpecifications.byPriority(priority));

        Page<TaskEntity> taskEntities = taskRepository.findAll(specification, pageable);

        return taskEntities.map(taskMapper::toResponseDTO);
    }

    // Получить задачи конкретного исполнителя
    public Page<TaskResponseDTO> getTasksByExecutor(Long executorId, int page, int size, TaskStatus status, TaskPriority priority) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<TaskEntity> specification = Specification.where(TaskSpecifications.byExecutor(executorId))
                .and(TaskSpecifications.byStatus(status))
                .and(TaskSpecifications.byPriority(priority));

        Page<TaskEntity> taskEntities = taskRepository.findAll(specification, pageable);

        return taskEntities.map(taskMapper::toResponseDTO);
    }

    // Редактирование задачи для администратора
    public TaskResponseDTO updateTaskByAdmin(TaskRequestDTO taskRequestDTO) throws TaskNotFoundException {
        TaskEntity existingTaskEntity = taskRepository.findById(taskRequestDTO.getId())
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена!"));

        if (!taskRequestDTO.getTitle().equals(existingTaskEntity.getTitle())) {
            existingTaskEntity.setTitle(taskRequestDTO.getTitle());
        }
        if (!taskRequestDTO.getDescription().equals(existingTaskEntity.getDescription())) {
            existingTaskEntity.setDescription(taskRequestDTO.getDescription());
        }
        if (taskRequestDTO.getStatus() != null) {
            existingTaskEntity.setStatus(taskRequestDTO.getStatus());
        }
        if (taskRequestDTO.getPriority() != null) {
            existingTaskEntity.setPriority(taskRequestDTO.getPriority());
        }
        if (taskRequestDTO.getExecutor() != null) {
            existingTaskEntity.setExecutor(taskRequestDTO.getExecutor());
        }
        // Добавлять поля к изменению — сюда
        taskRepository.saveAndFlush(existingTaskEntity);
        return taskMapper.toResponseDTO(existingTaskEntity);
    }

    // Универсальный Update-метод на случай, если пользователю позволят изменять другие поля
    public TaskResponseDTO updateTaskPropertyByUser(long id, Consumer<TaskEntity> updater) throws TaskNotFoundException {
        TaskEntity taskEntityForUpdating = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Задача не найдена."));
        updater.accept(taskEntityForUpdating);
        taskRepository.saveAndFlush(taskEntityForUpdating);
        return taskMapper.toResponseDTO(taskEntityForUpdating);
    }

    // Смена статуса задачи пользователем
    public TaskResponseDTO updateTaskStatusByUser(long id, TaskStatus status) throws TaskNotFoundException {
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
