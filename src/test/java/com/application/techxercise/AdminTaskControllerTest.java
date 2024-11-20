package com.application.techXercise;

import com.application.techXercise.controllers.AdminTaskController;
import com.application.techXercise.dto.TaskRequestDTO;
import com.application.techXercise.dto.TaskResponseDTO;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.services.TaskService;
import com.application.techXercise.utils.TaskPriority;
import com.application.techXercise.utils.TaskStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdminTaskControllerTest {

    @InjectMocks
    private AdminTaskController adminTaskController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskService taskService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminTaskController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void allTasks() throws Exception {
        when(taskService.getAllTasks()).thenReturn(new ArrayList<TaskResponseDTO>());
        mockMvc.perform(get("/api/admin/tasks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
        verify(taskService).getAllTasks();
    }

    @Test
    void createTaskTest() throws Exception {
        TaskRequestDTO taskEntity = new TaskRequestDTO(1L, "Тестовая задача 1", "Описание тестовой задачи 1"
                , TaskStatus.WAITING, TaskPriority.HIGH, new UserEntity()
                , new UserEntity(), new ArrayList<>());
        String taskJson = objectMapper.writeValueAsString(taskEntity);
        mockMvc.perform(post("/api/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated());
        verify(taskService).createTask(taskEntity);
    }

    @Test
    void getTaskByIdTest() throws Exception {
        TaskResponseDTO mockTask = new TaskResponseDTO(1L, "Тестовая задача", "Описание задачи",
                TaskStatus.WAITING, TaskPriority.HIGH, null, null, new ArrayList<>());
        when(taskService.getTaskById(1L)).thenReturn(mockTask);
        mockMvc.perform(get("/api/admin/tasks/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Тестовая задача"))
                .andExpect(jsonPath("$.description").value("Описание задачи"))
                .andExpect(jsonPath("$.priority").value(TaskPriority.HIGH.toString()));
    }

    @Test
    void updateTaskTest() throws Exception {
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO(1L, "Обновленная задача", "Обновленное описание", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM, new UserEntity(), new UserEntity(), new ArrayList<>());
        String taskJson = objectMapper.writeValueAsString(taskRequestDTO);
        TaskResponseDTO updatedTask = new TaskResponseDTO(1L, "Обновленная задача", "Обновленное описание", TaskStatus.IN_PROGRESS, TaskPriority.MEDIUM, new UserEntity(), new UserEntity(), new ArrayList<>());
        when(taskService.updateTaskByAdmin(taskRequestDTO)).thenReturn(updatedTask);
        mockMvc.perform(put("/api/admin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Обновленная задача"))
                .andExpect(jsonPath("$.description").value("Обновленное описание"));
    }
    @Test
    void deleteTaskTest() throws Exception {
        when(taskService.deleteTask(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/admin/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Zadacha udalena uspeshno."));
        when(taskService.deleteTask(2L)).thenReturn(false);
        mockMvc.perform(delete("/api/admin/{taskId}", 2L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTaskByIdFoundTest() throws Exception {
        TaskResponseDTO mockTask = new TaskResponseDTO(1L, "Тестовая задача", "Описание задачи", TaskStatus.WAITING, TaskPriority.HIGH, null, null, new ArrayList<>());
        when(taskService.getTaskById(1L)).thenReturn(mockTask);
        mockMvc.perform(get("/api/admin/tasks/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Тестовая задача"));
    }


}