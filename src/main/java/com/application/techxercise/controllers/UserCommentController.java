package com.application.techXercise.controllers;

import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.exceptions.CommentNotFoundException;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/tasks/{userId}/comments")
public class UserCommentController {

    CommentService commentService;

    public UserCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Создать комментарий
    @PostMapping("/")
    public ResponseEntity<CommentEntity> createComment(@PathVariable long taskId, @RequestBody CommentEntity commentEntity) throws TaskNotFoundException {
        CommentEntity createdComment = commentService.createComment(taskId, commentEntity);
        return createdComment != null ?
                ResponseEntity.ok(createdComment) :
                ResponseEntity.notFound().build();
    }

    // Получить комментарии пользователя
    @GetMapping("/")
    public ResponseEntity<List<CommentEntity>> getUserComments(@PathVariable long userId) throws CommentNotFoundException {
        List<CommentEntity> commentsCommenterById = commentService.getByCommenterId(userId);
        if (commentsCommenterById.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 если комментарии есть, но пустые
        }
        return ResponseEntity.ok(commentsCommenterById); // 200 если комментарии найдены
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
