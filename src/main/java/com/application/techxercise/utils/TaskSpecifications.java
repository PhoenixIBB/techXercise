package com.application.techXercise.utils;

import com.application.techXercise.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<TaskEntity> byAuthor(Long authorId) {
        if (authorId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<TaskEntity> byExecutor(Long executorId) {
        if (executorId == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("executor").get("id"), executorId);
    }

    public static Specification<TaskEntity> byStatus(TaskStatus status) {
        if (status == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<TaskEntity> byPriority(TaskPriority priority) {
        if (priority == null) {
            return null;
        }
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }
}
