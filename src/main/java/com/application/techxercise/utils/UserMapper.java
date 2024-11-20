package com.application.techXercise.utils;

import com.application.techXercise.dto.UserRequestDTO;
import com.application.techXercise.dto.UserResponseDTO;
import com.application.techXercise.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toResponseDTO(UserEntity userEntity);

    UserEntity fromRequestDTO(UserRequestDTO userRequestDTO);

}
