package com.application.techXercise.services;

import com.application.techXercise.entity.Task;
import com.application.techXercise.entity.User;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.repositories.TaskRepository;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
public class TaskService {

    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;

    // Общие CRUD-методы за исключением UPDATE, так как его мы детальнее реализуем ниже

    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        if (tasks.isEmpty()) {
            return new ArrayList<>();
        }
        return tasks;
    }

    public Task getTaskById(long id) throws TaskNotFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена!"));
    }

    public Task createTask(Task task, long authorId, long executorId) throws UserNotFoundException {
        User author = userRepository.findById(authorId).orElseThrow(() -> new UserNotFoundException("Автор не найден!"));
        User executor = userRepository.findById(executorId).orElseThrow(() -> new UserNotFoundException("Исполнитель не найден!"));
        task.setAuthor(author);
        task.setExecutor(executor);
        taskRepository.saveAndFlush(task);
        return task;
    }

    public boolean deleteTask(long id) {
        taskRepository.deleteById(id);
        return true;
    }

    // Общий метод изменения полей задач

    public Task updateTaskProperty(long id, Consumer<Task> updater) throws TaskNotFoundException {
        Task taskForChanging = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена, нечего редактировать!"));
        updater.accept(taskForChanging);
        return taskRepository.saveAndFlush(taskForChanging);
    }

    public Task updateTaskStatus(long id, TaskStatus status) throws TaskNotFoundException {
        return updateTaskProperty(id, task -> task.setStatus(status));
    }

    public Task updateTaskPriority(long id, TaskPriority taskPriority) throws TaskNotFoundException {
        return updateTaskProperty(id, task -> task.setPriority(taskPriority));
    }

    public Task updateTaskExecutor(long id, long userId) throws UserNotFoundException, TaskNotFoundException {
        User executor = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь не найден!"));
        return updateTaskProperty(id, task -> { task.setExecutor(executor); });
    }

}
