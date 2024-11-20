package com.application.techXercise.utils;

import com.application.techXercise.entity.TaskEntity;
import com.application.techXercise.dto.TaskRequestDTO;
import com.application.techXercise.dto.TaskResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponseDTO toResponseDTO(TaskEntity taskEntity);

    TaskEntity fromRequestDTO(TaskRequestDTO taskRequestDTO);

}
