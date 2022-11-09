package ru.gb.hubr.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "mail-service")
@RequiredArgsConstructor
public class MailProperties {

    private String host;

    private int port;

    private String username;

    private String password;

    private String pathTemplate;

    private String displayFrom;

    private String baseUrl;
}
