//package com.application.techXercise.utils;
//
//import com.application.techXercise.entity.Task;
//
//import java.util.Objects;
//
//public class TaskDTO {
//
//    // Класс для передачи в запросе только необходимых данных
//
//    private String title;
//    private String description;
//    private TaskStatus status;
//    private TaskPriority priority;
//
//    public TaskDTO() {
//    }
//
//    public TaskDTO(String title, String description, TaskStatus status, TaskPriority priority) {
//        this.title = title;
//        this.description = description;
//        this.status = status;
//        this.priority = priority;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public TaskStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(TaskStatus status) {
//        this.status = status;
//    }
//
//    public TaskPriority getPriority() {
//        return priority;
//    }
//
//    public void setPriority(TaskPriority priority) {
//        this.priority = priority;
//    }
//
//    @Override
//    public String toString() {
//        return "TaskDTO{" +
//                "title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", status=" + status +
//                ", priority=" + priority +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TaskDTO taskDTO = (TaskDTO) o;
//        return Objects.equals(title, taskDTO.title) && Objects.equals(description, taskDTO.description) && Objects.equals(status, taskDTO.status) && Objects.equals(priority, taskDTO.priority);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(title, description, status, priority);
//    }
//}
