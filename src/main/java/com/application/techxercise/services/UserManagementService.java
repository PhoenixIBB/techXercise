package com.application.techXercise.services;

import com.application.techXercise.dto.UserRequestDTO;
import com.application.techXercise.dto.UserResponseDTO;
import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.exceptions.UserNotFoundException;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserManagementService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserManagementService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    // Создать пользователя
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.findByEmail(userRequestDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует!");
        }
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        UserEntity userEntity = userMapper.fromRequestDTO(userRequestDTO);
        userRepository.saveAndFlush(userEntity);
        return userMapper.toResponseDTO(userEntity);
    }

    // Получить всех пользователей
    public List<UserResponseDTO> getAllUsers() throws UserNotFoundException {
        List<UserEntity> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserNotFoundException("Пользователи не найдены.");
        }
        return users.stream()
                .map(userMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Получить пользователя по ID
    public UserResponseDTO getUserById(long id) throws UserNotFoundException {
        return userMapper.toResponseDTO(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с таким id не найден!")));
    }

    // Получить пользователя по email
    public UserResponseDTO getUserByEmail(String email) {
        return userMapper.toResponseDTO(userRepository.findByEmail(email));
    }

    // Универсальный Update-метод
    public UserResponseDTO updateUserProperty(long id, Consumer<UserEntity> updater) throws UserNotFoundException {
        UserEntity userForUpdating = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь не найден."));
        updater.accept(userForUpdating);
        return userMapper.toResponseDTO(userRepository.saveAndFlush(userForUpdating));
    }

    // Сменить адрес почты
    public UserResponseDTO updateUserEmail(long id, String email) throws UserNotFoundException {
        return updateUserProperty(id, user -> user.setEmail(email));
    }

    // Сменить пароль   (подумать над безопасностью и шифрованием)
    public UserResponseDTO updateUserPassword(long userId, String newPassword) throws UserNotFoundException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        String encodedPassword = passwordEncoder.encode(newPassword);

        userEntity.setPassword(encodedPassword);
        userRepository.saveAndFlush(userEntity);

        return userMapper.toResponseDTO(userEntity);
    }

    // Удалить пользователя
    public boolean deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

}
