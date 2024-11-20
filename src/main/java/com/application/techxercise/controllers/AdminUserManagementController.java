package com.application.techXercise.controllers;

import com.application.techXercise.dto.UserRequestDTO;
import com.application.techXercise.dto.UserResponseDTO;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.services.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user-settings")
public class AdminUserManagementController {

    UserManagementService userManagementService;

    @Autowired
    public AdminUserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userManagementService.createUser(userRequestDTO));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() throws UserNotFoundException {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long userId) throws UserNotFoundException {
        UserResponseDTO userEntity = userManagementService.getUserById(userId);
        return userEntity != null ?
                ResponseEntity.ok(userEntity) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) throws UserNotFoundException {
        return userManagementService.deleteUser(userId) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

}
