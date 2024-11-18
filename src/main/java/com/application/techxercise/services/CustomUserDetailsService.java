package com.application.techXercise.services;

import com.application.techXercise.entity.UserEntity;
import com.application.techXercise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) { this.userRepository = userRepository; }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) { this.passwordEncoder = passwordEncoder; }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        return User.builder()
                .username(userEntity.getEmail())
                .password(passwordEncoder.encode((userEntity.getPassword())))
                .roles(userEntity.getRolesString())
                .build();
    }
}
