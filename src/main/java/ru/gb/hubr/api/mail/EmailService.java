package ru.gb.hubr.api.mail;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendMail(EmailContext EmailContext) throws MessagingException, UnsupportedEncodingException;
}
