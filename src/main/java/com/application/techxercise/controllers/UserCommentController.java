package com.application.techXercise.controllers;

import com.application.techXercise.dto.CommentResponseDTO;
import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.exceptions.CommentNotFoundException;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/user/tasks/{taskId}/comments")
public class UserCommentController {

    CommentService commentService;

    @Autowired
    public UserCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Создать комментарий
    @PostMapping("/")
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable long taskId, @Valid @RequestBody String content) throws UserNotFoundException {
        CommentResponseDTO createdComment = commentService.createComment(taskId, content);
        return createdComment != null ?
                ResponseEntity.ok(createdComment) :
                ResponseEntity.notFound().build();
    }

    // Получить комментарии пользователя
    @GetMapping("/")
    public ResponseEntity<Page<CommentResponseDTO>> getCommentsForTask(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String date) {
        LocalDate parsedDate = null;
        if (date != null) {
            try {
                parsedDate = LocalDate.parse(date);
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
        }
        Page<CommentResponseDTO> comments = commentService.getCommentsForTask(taskId, parsedDate, page, size);
        return ResponseEntity.ok(comments);
    }

    // Редактировать комментарий
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateCommentContent(@PathVariable long commentId
            , @Valid @RequestBody String commentContent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            CommentResponseDTO existingComment = commentService.getCommentById(commentId);
            if (!existingComment.getCommenter().getEmail().equals(currentUserEmail)) {
                return ResponseEntity.status(403).build();
            }
            CommentResponseDTO updatedCommentEntity = commentService.updateCommentContent(commentId, commentContent);
            return ResponseEntity.ok(updatedCommentEntity);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить комментарий
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable long commentId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            CommentResponseDTO existingComment = commentService.getCommentById(commentId);
            if (!existingComment.getCommenter().getEmail().equals(currentUserEmail)) {
                return ResponseEntity.status(403).build();
            }
            boolean deleted = commentService.deleteComment(commentId);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
