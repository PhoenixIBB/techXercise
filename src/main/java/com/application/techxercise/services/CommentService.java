package com.application.techXercise.services;

import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.exceptions.CommentNotFoundException;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.repositories.CommentRepository;
import com.application.techXercise.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    // Создать комментарий
    public CommentEntity createComment(long taskId, CommentEntity commentEntity) throws TaskNotFoundException {
        // Проверяем, существует ли задача с указанным taskId
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Задача с id " + taskId + " не найдена."));

        // Привязываем задачу к комментарию
        commentEntity.setCommentedTask(taskEntity);

        // Сохраняем комментарий в базе данных
        return commentRepository.saveAndFlush(commentEntity);
    }

//    // Получить все комментарии
//    public List<CommentEntity> getAllComments() {
//        return commentRepository.findAll();
//    }

    // Получить комментарии к задаче
    public List<CommentEntity> getTaskComments(long taskId) {
        return commentRepository.findByCommentedTaskEntityId(taskId);
    }

    // Получить все комментарии пользователя
    public List<CommentEntity> getByCommenterId(long commenterId) throws CommentNotFoundException {
        List<CommentEntity> comments = commentRepository.findByCommenterId(commenterId);
        if (comments.isEmpty()) {
            throw new CommentNotFoundException("Комментарии пользователя с id " + commenterId + " не найдены.");
        }
        return comments;
    }

    // Получить комментарий по id
    public CommentEntity getCommentById(long commentId) throws CommentNotFoundException {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Комментарий не найден!"));
    }

    // Универсальный Update-метод
    public CommentEntity updateCommentProperty(long id, Consumer<CommentEntity> updater) throws CommentNotFoundException {
        CommentEntity commentEntityForUpdating = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Комментарий не найден."));
        updater.accept(commentEntityForUpdating);
        return commentRepository.saveAndFlush(commentEntityForUpdating);
    }

    // Отредактировать комментарий
    public CommentEntity updateCommentContent(long id, String content) throws CommentNotFoundException {
        return updateCommentProperty(id, comment -> comment.setContent(content));
    }

    // Удалить комментарий
    public boolean deleteComment(long commentId) throws CommentNotFoundException, AccessDeniedException {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Не найден комментарий с id " + commentId));
        commentRepository.delete(commentEntity);
        return true;
    }

}
