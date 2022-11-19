package ru.gb.hubr.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.gb.hubr.controller.profile.TestConstants;


/**
 * @author Vitaly Krivobokov
 * @Date 14.11.2022
 */

@SpringJUnitConfig
@SpringBootTest
public class TestConfig {



    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(TestConstants.PROFILE_USER, TestConstants.BLOCKED_USER);
    }



}
