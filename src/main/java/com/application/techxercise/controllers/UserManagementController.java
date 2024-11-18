package com.application.techXercise.controllers;

import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.services.UserManagementService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/{userId}/user-settings")
public class UserManagementController {

    UserManagementService userManagementService;

    public UserManagementController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PatchMapping("/email")
    public ResponseEntity<UserEntity> updateEmail(@PathVariable long userId, @RequestParam String email) throws UserNotFoundException {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userManagementService.getUserByEmail(currentUserEmail);
        if (currentUser.getId() != userId) {
            throw new SecurityException("Вы не можете редактировать данные другого пользователя.");
        }
        UserEntity userEntity = userManagementService.updateUserEmail(userId, email);
        return userEntity != null ?
                ResponseEntity.ok(userEntity) :
                ResponseEntity.notFound().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<UserEntity> updatePassword(@PathVariable long userId, @RequestParam String password) throws UserNotFoundException {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userManagementService.getUserByEmail(currentUserEmail);
        if (currentUser.getId() != userId) {
            throw new SecurityException("Вы не можете редактировать данные другого пользователя.");
        }
        UserEntity userEntity = userManagementService.updateUserPassword(userId, password);
        return userEntity != null ?
                ResponseEntity.ok(userEntity) :
                ResponseEntity.notFound().build();
    }


}
