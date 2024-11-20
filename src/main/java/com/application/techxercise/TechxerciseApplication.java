package com.application.techXercise;

import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.repositories.CommentRepository;
import com.application.techXercise.repositories.TaskRepository;
import com.application.techXercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class TechxerciseApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private CommentRepository commentRepository;

	public static void main(String[] args) {
		SpringApplication.run(TechxerciseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		UserEntity UserEntity = new UserEntity("Рамиль", "Гильманов", "phnxibb@gmail.com", "TECHX", Role.ADMIN, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//		UserEntity UserEntity1 = new UserEntity("Алексей", "Петров", "alexey123@gmail.com", "ALEXALEX", Role.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//		UserEntity UserEntity2 = new UserEntity("Мария", "Смирнова", "maria_smirnova@mail.ru", "MARIA2024", Role.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//		UserEntity UserEntity3 = new UserEntity("Дмитрий", "Кузнецов", "dmitry_kuznetsov@yandex.ru", "DIMA5678", Role.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//		UserEntity UserEntity4 = new UserEntity("Екатерина", "Васильева", "katya.vas@yandex.com", "EKATERINA_99", Role.USER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
//
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
