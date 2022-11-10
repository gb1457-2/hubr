package ru.gb.hubr.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class EmailConfig {


    private final MailProperties mailProperties;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.host", mailProperties.getHost());
        props.put("mail.smtp.port", mailProperties.getPort());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.socketFactory.port", mailProperties.getPort());
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.ssl.trust", mailProperties.getHost());

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(mailProperties.getUsername(),
                                mailProperties.getPassword());
                    }
                });
        mailSender.setSession(session);
        return mailSender;
    }

}
