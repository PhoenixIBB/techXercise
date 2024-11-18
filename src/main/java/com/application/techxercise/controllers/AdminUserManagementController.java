package com.application.techXercise.controllers;

import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.services.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user-settings")
public class AdminUserManagementController {

    UserManagementService userManagementService;

    public AdminUserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        return ResponseEntity.ok(userManagementService.createUser(userEntity));
    }

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> getAllUsers() throws UserNotFoundException {
        return ResponseEntity.ok(userManagementService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable long userId) throws UserNotFoundException {
        UserEntity userEntity = userManagementService.getUserById(userId);
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
