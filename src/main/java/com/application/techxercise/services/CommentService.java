package com.application.techXercise.services;

import com.application.techXercise.entity.Comment;
import com.application.techXercise.entity.Task;
import com.application.techXercise.exceptions.CommentNotFoundException;
import com.application.techXercise.exceptions.TaskNotFoundException;
import com.application.techXercise.repositories.CommentRepository;
import com.application.techXercise.repositories.TaskRepository;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.utils.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public List<Comment> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return comments;
    }

    public Comment getCommentById(long commentId) throws CommentNotFoundException {
        return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Не найден комментарий с id " + commentId));
    }

    public Comment updateCommentProperty(long id, Consumer<Comment> updater) {
        Comment commentForChanging = commentRepository.findById(id).orElse(null);
        updater.accept(commentForChanging);
        return commentRepository.saveAndFlush(commentForChanging);
    }

    public Comment updateCommentContent(long id, String content) {
        return updateCommentProperty(id, comment -> comment.setContent(content));
    }

    public Comment createComment(long taskId, Comment comment) throws TaskNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Задача не найдена! Невозможно создать комментарий."));
        comment.setCommentedTask(task);
        commentRepository.saveAndFlush(comment);
        return comment;
    }

    public boolean deleteComment(long commentId, long userId) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Не найден комментарий с id " + commentId));
        if (comment != null && comment.getCommenter().getId() == userId) {
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }

    // Особые методы

    public List<Comment> getByAuthorId(long commenterId) {
//        return userRepository.findById(authorId).getCommentsCreated();
        return commentRepository.findByCommenterId(commenterId);
    }

    public List<Comment> getByCommentDate(LocalDate commentCreationDate) {
        return commentRepository.findByCommentDate(commentCreationDate);
    }


}
