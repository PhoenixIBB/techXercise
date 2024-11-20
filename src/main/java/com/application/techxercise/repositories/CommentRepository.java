package com.application.techXercise.repositories;

import com.application.techXercise.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    Page<CommentEntity> findByCommenterId(long commenterId, Pageable pageable);

    // Я нормальный, честно, просто нужно сделать хоть какую-то фильтрацию, а я не успеваю :)
    Page<CommentEntity> findByCommentedTaskEntityIdAndCommentCreationDateAfter(Long taskEntityId, LocalDate commentCreationDate, Pageable pageable);

}
