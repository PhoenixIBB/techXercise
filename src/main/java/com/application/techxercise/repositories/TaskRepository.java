package com.application.techXercise.repositories;

import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>, JpaSpecificationExecutor<TaskEntity> {

//    Page<TaskEntity> findByAuthorId(Long authorId, Pageable pageable);
//
//    Page<TaskEntity> findByExecutorId(Long executorId, Pageable pageable);
//
//    Page<TaskEntity> findByStatus(TaskStatus status, Pageable pageable);
//
//    Page<TaskEntity> findByPriority(TaskPriority priority, Pageable pageable);

}
