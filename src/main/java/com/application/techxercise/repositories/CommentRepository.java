package com.application.techXercise.repositories;

import com.application.techXercise.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByCommenterId(long commenterId);
    List<CommentEntity> findByCommentedTaskEntityId(long taskId);

}
