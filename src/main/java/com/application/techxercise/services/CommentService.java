package com.application.techXercise.services;

import com.application.techXercise.dto.CommentResponseDTO;
import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.CommentNotFoundException;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.repositories.CommentRepository;
import com.application.techXercise.repositories.TaskRepository;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.utils.CommentMapper;
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
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.commentMapper = commentMapper;
    }

    // Создать комментарий
    public CommentResponseDTO createComment(long taskId, String content) throws UserNotFoundException {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userRepository.findByEmail(currentUserEmail);
        if (currentUser == null) {
            throw new UserNotFoundException("Текущий пользователь не найден.");
        }
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(content);
        commentEntity.setCommentedTaskEntity(taskRepository.findById(taskId).orElse(null));
        commentEntity.setCommenter(currentUser);
        commentEntity.setCommentCreationDate(LocalDate.now());
        return commentMapper.toResponseDTO(commentRepository.save(commentEntity));
    }

    // Получить комментарии к задаче
    public Page<CommentResponseDTO> getCommentsForTask(Long taskId, LocalDate commentCreationDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> comments = commentRepository.findByCommentedTaskEntityIdAndCommentCreationDateAfter(taskId, commentCreationDate, pageable);
        return comments.map(commentMapper::toResponseDTO);
    }

    // Получить все комментарии пользователя
    public Page<CommentResponseDTO> getByCommenterId(long commenterId, int page, int size) throws CommentNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentEntity> comments = commentRepository.findByCommenterId(commenterId, pageable);
        if (comments.isEmpty()) {
            return Page.empty();
        }
        return comments.map(commentMapper::toResponseDTO);
    }

    // Получить комментарий по id
    public CommentResponseDTO getCommentById(long commentId) throws CommentNotFoundException {
        return commentMapper.toResponseDTO(commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Комментарий не найден!")));
    }

    // Универсальный Update-метод
    public CommentResponseDTO updateCommentProperty(long id, Consumer<CommentEntity> updater) throws CommentNotFoundException {
        CommentEntity commentEntityForUpdating = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException("Комментарий не найден."));
        updater.accept(commentEntityForUpdating);
        return commentMapper.toResponseDTO(commentRepository.saveAndFlush(commentEntityForUpdating));
    }

    // Отредактировать комментарий
    public CommentResponseDTO updateCommentContent(long id, String content) throws CommentNotFoundException {
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
