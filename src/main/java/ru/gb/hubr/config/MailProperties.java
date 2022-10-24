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
@RequiredArgsConstructor
public class MailProperties {

    @Value("${mail-service.host}")
    private String host;
    @Value("${mail-service.port}")
    private int port;
    @Value("${mail-service.login-mail}")
    private String username;
    @Value("${mail-service.password-mail}")
    private String password;

    @Value("${mail-service.path-template}")
    private String pathTemplate;

    @Value("${mail-service.display-from}")
    private String displayFrom;

    @Value("${mail-service.base-url}")
    private String baseURL;

}
