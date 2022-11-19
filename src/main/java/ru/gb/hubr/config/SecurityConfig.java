package ru.gb.hubr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(requests -> {
            requests.antMatchers("/articles/all").permitAll();
            requests.antMatchers("/articles/add").access("hasAuthority('article.write')");
            requests.antMatchers("/login").permitAll();
            requests.antMatchers("/register").permitAll();
            requests.antMatchers("/").permitAll();
        });
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin().loginPage("/login").defaultSuccessUrl("/articles/all", true);
        http.exceptionHandling().accessDeniedPage("/errors/access-denied");
        http.csrf().disable();
        http.httpBasic();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}