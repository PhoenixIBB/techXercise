package com.application.techXercise.repositories;

import com.application.techXercise.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findByAuthorId(Long authorId);    // по автору
    List<TaskEntity> findByExecutorId(Long executorId);    // по исполнителю

}
