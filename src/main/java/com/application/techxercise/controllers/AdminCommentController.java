package com.application.techXercise.controllers;

import com.application.techXercise.dto.CommentResponseDTO;
import com.application.techXercise.dto.PagedResponseDTO;
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
    public ResponseEntity<CommentResponseDTO> createComment(@PathVariable long taskId, @Valid @RequestBody String content) throws UserNotFoundException {
        CommentResponseDTO commentResponseDTO = commentService.createComment(taskId, content);
        return commentResponseDTO != null ?
                ResponseEntity.ok(commentResponseDTO) :
                ResponseEntity.notFound().build();
    }

    // Получить все комментарии к задаче
    @GetMapping("/")
    public ResponseEntity<PagedResponseDTO<CommentResponseDTO>> getCommentsForTask(
            @PathVariable Long taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PagedResponseDTO<CommentResponseDTO> comments = commentService.getCommentsForTask(taskId, page, size);
        return ResponseEntity.ok(comments);
    }

    // Редактировать комментарий
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateCommentContent(@PathVariable long commentId, @Valid @RequestBody String commentContent) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            CommentResponseDTO existingComment = commentService.getCommentById(commentId);
            if (!existingComment.getCommenter().getEmail().equals(currentUserEmail)) {
                throw new SecurityException("Вы не являетесь автором этого комментария и не можете его редактировать.");
            }
            CommentResponseDTO updatedCommentEntity = commentService.updateCommentContent(commentId, commentContent);
            return ResponseEntity.ok(updatedCommentEntity);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Удалить комментарий
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable long commentId) throws CommentNotFoundException {
        return commentService.deleteComment(commentId) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

}
