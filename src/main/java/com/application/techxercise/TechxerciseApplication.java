package com.application.techXercise;

import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.repositories.CommentRepository;
import com.application.techXercise.repositories.TaskRepository;
import com.application.techXercise.repositories.UserRepository;
import com.application.techXercise.services.UserManagementService;
import com.application.techXercise.utils.Role;
import com.application.techXercise.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class TechxerciseApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private UserManagementService userManagementService;
	@Autowired
	UserMapper userMapper;

	public static void main(String[] args) {
		SpringApplication.run(TechxerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		UserEntity userEntity = new UserEntity("Рамиль", "Гильманов", "phnxibb@gmail.com", "TechMeh123%", Role.ADMIN, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//		UserEntity userEntity1 = new UserEntity("Алексей", "Петров", "alexey123@gmail.com", "TechMeh123%", Role.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//		UserEntity userEntity2 = new UserEntity("Мария", "Смирнова", "maria_smirnova@mail.ru", "TechMeh123%", Role.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//		UserEntity userEntity3 = new UserEntity("Дмитрий", "Кузнецов", "dmitry_kuznetsov@yandex.ru", "TechMeh123%", Role.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//		UserEntity userEntity4 = new UserEntity("Екатерина", "Васильева", "katya.vas@yandex.com", "TechMeh123%", Role.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//
//		userManagementService.createUser(userMapper.toRequestDTO(userEntity));
//		userManagementService.createUser(userMapper.toRequestDTO(userEntity1));
//		userManagementService.createUser(userMapper.toRequestDTO(userEntity2));
//		userManagementService.createUser(userMapper.toRequestDTO(userEntity3));
//		userManagementService.createUser(userMapper.toRequestDTO(userEntity4));

//		userRepository.saveAndFlush(UserEntity);
//		userRepository.saveAndFlush(UserEntity1);
//		userRepository.saveAndFlush(UserEntity2);
//		userRepository.saveAndFlush(UserEntity3);
//		userRepository.saveAndFlush(UserEntity4);

//		myPasswordEncoderService.updatePasswords();

		List<UserEntity> users = userRepository.findAll();

		for(UserEntity user : users) {
			System.out.println("Login: " + user.getEmail() + "\n" + "Password: " + user.getPassword());
		}




	}
}
