package com.application.techXercise.controllers;

import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.exceptions.CommentNotFoundException;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/tasks/{userId}/{taskId}/comments")
public class UserCommentController {

    CommentService commentService;

    public UserCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Создать комментарий
    @PostMapping("/")
    public ResponseEntity<CommentEntity> createComment(@PathVariable long taskId, @RequestBody String content) throws TaskNotFoundException, UserNotFoundException {
        CommentEntity createdComment = commentService.createComment(taskId, content);
        return createdComment != null ?
                ResponseEntity.ok(createdComment) :
                ResponseEntity.notFound().build();
    }

    // Получить комментарии пользователя
    @GetMapping("/")
    public ResponseEntity<List<CommentEntity>> getUserComments(@PathVariable long userId) throws CommentNotFoundException {
        List<CommentEntity> commentsByCommenterId = commentService.getByCommenterId(userId);
        if (commentsByCommenterId.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(commentsByCommenterId);
    }

    // Редактировать комментарий
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentEntity> updateCommentContent(@PathVariable long commentId, @RequestBody String commentContent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            CommentEntity existingComment = commentService.getCommentById(commentId);
            if (!existingComment.getCommenter().getEmail().equals(currentUserEmail)) {
                throw new SecurityException("Вы не являетесь автором этого комментария и не можете его редактировать.");
            }
            CommentEntity updatedCommentEntity = commentService.updateCommentContent(commentId, commentContent);
            return ResponseEntity.ok(updatedCommentEntity);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить комментарий
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentEntity> deleteComment(@PathVariable long commentId) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            CommentEntity existingComment = commentService.getCommentById(commentId);
            if (!existingComment.getCommenter().getEmail().equals(currentUserEmail)) {
                throw new SecurityException("Вы не являетесь автором этого комментария и не можете его удалить.");
            }
            boolean deleted = commentService.deleteComment(commentId);
            return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
