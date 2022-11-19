package ru.gb.hubr.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author Vitaly Krivobokov
 * @Date 13.11.22
 */
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
