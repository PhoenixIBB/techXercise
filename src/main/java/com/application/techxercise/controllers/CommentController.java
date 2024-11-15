package com.application.techXercise.controllers;

import com.application.techXercise.entity.Comment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/{userId}/tasks/{taskId}/comments")
public class CommentController {

//    @PostMapping("/{taskId}/comments")
//    public ResponseEntity<Comment> addComment(@PathVariable long taskId, @RequestBody Comment comment) {
//        Comment createdComment = taskService.addComment(taskId, comment);
//
//        if (createdComment != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
