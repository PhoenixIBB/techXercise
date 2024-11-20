package com.application.techXercise.controllers;

import com.application.techXercise.dto.UserResponseDTO;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.services.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/{userId}/user-settings")
public class UserManagementController {

    UserManagementService userManagementService;

    @Autowired
    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PatchMapping("/email")
    public ResponseEntity<UserResponseDTO> updateEmail(@PathVariable long userId, @Valid @RequestParam String email) throws UserNotFoundException {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponseDTO currentUser = userManagementService.getUserByEmail(currentUserEmail);
        if (currentUser.getId() != userId) {
            throw new SecurityException("Вы не можете редактировать данные другого пользователя.");
        }
        UserResponseDTO userEntity = userManagementService.updateUserEmail(userId, email);
        return userEntity != null ?
                ResponseEntity.ok(userEntity) :
                ResponseEntity.notFound().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<UserResponseDTO> updatePassword(@PathVariable long userId, @Valid @RequestParam String password) throws UserNotFoundException {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponseDTO currentUser = userManagementService.getUserByEmail(currentUserEmail);
        if (currentUser.getId() != userId) {
            throw new SecurityException("Вы не можете редактировать данные другого пользователя.");
        }
        UserResponseDTO userEntity = userManagementService.updateUserPassword(userId, password);
        return userEntity != null ?
                ResponseEntity.ok(userEntity) :
                ResponseEntity.notFound().build();
    }


}
