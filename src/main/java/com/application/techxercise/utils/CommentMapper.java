package com.application.techXercise.utils;

import com.application.techXercise.dto.CommentRequestDTO;
import com.application.techXercise.dto.CommentResponseDTO;
import com.application.techXercise.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentResponseDTO toResponseDTO(CommentEntity commentEntity);

    CommentEntity fromRequestDTO(CommentRequestDTO commentRequestDTO);

}
