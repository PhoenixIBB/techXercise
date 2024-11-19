package com.application.techXercise.controllers;

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

@RestController
@RequestMapping("/api/admin/tasks/{taskId}/comments")
public class AdminCommentController {

    CommentService commentService;

    @Autowired
    public AdminCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Создать комментарий
    @PostMapping("/")
    public ResponseEntity<CommentEntity> createComment(@PathVariable long taskId, @Valid @RequestBody String content) throws TaskNotFoundException, UserNotFoundException {
        CommentEntity createdComment = commentService.createComment(taskId, content);
        return createdComment != null ?
                ResponseEntity.ok(createdComment) :
                ResponseEntity.notFound().build();
    }

    // Получить все комментарии к задаче
    @GetMapping("/")
    public ResponseEntity<Page<CommentEntity>> getCommentsForTask(
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

        Page<CommentEntity> comments = commentService.getCommentsForTask(taskId, parsedDate, page, size);
        return ResponseEntity.ok(comments);
    }

    // Редактировать комментарий
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentEntity> updateCommentContent(@PathVariable long commentId, @Valid @RequestBody String commentContent) {
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
