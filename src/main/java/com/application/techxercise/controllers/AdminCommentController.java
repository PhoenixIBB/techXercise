package com.application.techXercise.controllers;

import com.application.techXercise.entity.CommentEntity;
import com.application.techXercise.exceptions.CommentNotFoundException;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tasks/{taskId}/comments")
public class AdminCommentController {

    CommentService commentService;

    public AdminCommentController(CommentService commentService) {
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

    // Получить все комментарии к задаче
    @GetMapping("/")
    public ResponseEntity<List<CommentEntity>> getTaskComments(@PathVariable long taskId) {
        List<CommentEntity> taskComments = commentService.getTaskComments(taskId);
        return ResponseEntity.ok(taskComments);
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
    public ResponseEntity<CommentEntity> deleteComment(@PathVariable long commentId) throws CommentNotFoundException {
        return commentService.deleteComment(commentId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

}
