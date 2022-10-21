package ru.gb.hubr.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("classpath:mail-property.properties")
@RequiredArgsConstructor
public class MailProperties {

    @Value("${host}")
    private String host;
    @Value("${port}")
    private int port;
    @Value("${login-mail}")
    private String username;
    @Value("${password-mail}")
    private String password;

    @Value("${path-template}")
    private String pathTemplate;

    @Value("${display-from}")
    private String displayFrom;

}
