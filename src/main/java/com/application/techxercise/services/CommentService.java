package com.application.techXercise.services;

import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.CommentNotFoundException;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.repositories.CommentRepository;
import com.application.techXercise.repositories.TaskRepository;
import com.application.techXercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    // Создать комментарий
    public CommentEntity createComment(long taskId, String content) throws TaskNotFoundException, UserNotFoundException {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userRepository.findByEmail(currentUserEmail);
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(content);
        commentEntity.setCommentedTask(taskRepository.findById(taskId).orElse(null));
        commentEntity.setCommenter(currentUser);
        commentEntity.setCommentCreationDate(LocalDate.now());
        return commentRepository.save(commentEntity);
    }

    // Получить комментарии к задаче
    public Page<CommentEntity> getCommentsForTask(Long taskId, LocalDate commentCreationDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByCommentedTaskEntityIdAndCommentCreationDateAfter(taskId, commentCreationDate, pageable);
    }

    // Получить все комментарии пользователя
    public Page<CommentEntity> getByCommenterId(long commenterId, int page, int size) throws CommentNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> comments = commentRepository.findByCommenterId(commenterId, pageable);
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
