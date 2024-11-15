package com.application.techXercise.services;

import com.application.techXercise.entity.User;
import com.application.techXercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);   // Временный метод
    }

    public void deleteUser(long id) { userRepository.deleteById(id); }

    // Особая логика

}
