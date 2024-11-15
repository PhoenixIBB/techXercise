package com.application.techXercise.repositories;

import com.application.techXercise.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByAuthor(String author);
    List<Comment> findByCommentDate(LocalDate date);
    List<Comment> findByCommenterId(long authorId);

}
