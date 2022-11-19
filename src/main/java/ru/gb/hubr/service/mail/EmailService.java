package ru.gb.hubr.service.mail;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
/**
 * @author Vitaly Krivobokov
 * @Date 13.11.22
 */
//todo vitaly сейчас почта работает в синхроне, блочит сессию, нужно отправку сделать в ассинхроне, вариант запись в брокер, и вычитка по регламенту
public interface EmailService {
    void sendMail(EmailContext EmailContext) throws MessagingException, UnsupportedEncodingException;
}
