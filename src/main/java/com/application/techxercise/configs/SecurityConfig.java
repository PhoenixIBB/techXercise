//package com.application.techXercise.configs;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()  // Отключаем CSRF для REST API
//                .authorizeRequests()
//                .antMatchers("/api/auth/login", "/api/auth/register").permitAll()  // Разрешаем доступ к страницам регистрации и входа
//                .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
//                .and()
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()));  // Добавляем фильтр JWT
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(new JwtAuthenticationProvider());  // Поставим кастомный провайдер для JWT
//    }
//}