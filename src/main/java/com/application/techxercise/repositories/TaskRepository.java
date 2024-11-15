package com.application.techXercise.repositories;

import com.application.techXercise.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthorId(Long authorId);    // по автору
    List<Task> findByExecutorId(Long assigneeId);    // по исполнителю

}
