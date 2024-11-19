package com.application.techXercise.services;

import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;

@Service
@Transactional
public class UserManagementService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserManagementService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // Создать пользователя
    public UserEntity createUser(UserEntity userEntity) {
        if (userRepository.findByEmail(userEntity.getEmail()) != null) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует!");
        }
        return userRepository.saveAndFlush(userEntity);
    }

    // Получить всех пользователей
    public List<UserEntity> getAllUsers() throws UserNotFoundException {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Пользователи не найдены.");
        }
        return users;
    }

    // Получить пользователя по ID
    public UserEntity getUserById(long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден!"));
    }

    // Получить пользователя по email
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Универсальный Update-метод
    public UserEntity updateUserProperty(long id, Consumer<UserEntity> updater) throws UserNotFoundException {
        UserEntity userForUpdating = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        updater.accept(userForUpdating);
        return userRepository.saveAndFlush(userForUpdating);
    }

    // Сменить адрес почты
    public UserEntity updateUserEmail(long id, String email) throws UserNotFoundException {
        return updateUserProperty(id, user -> user.setEmail(email));
    }

    // Сменить пароль   (подумать над безопасностью и шифрованием)
    public UserEntity updateUserPassword(long id, String rawPassword) throws UserNotFoundException {
        return updateUserProperty(id, user -> user.setPassword(rawPassword));
    }

    // Удалить пользователя
    public boolean deleteUser(long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

}
