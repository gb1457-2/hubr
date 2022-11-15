package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.config.MailProperties;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.EventUserDao;
import ru.gb.hubr.entity.user.AccountUser;
import ru.gb.hubr.entity.EventUser;
import ru.gb.hubr.entity.TypeEvent;
import ru.gb.hubr.service.mail.EmailContext;
import ru.gb.hubr.service.mail.EmailService;

/**
 * @author Vitaly
 * @Date 11.11.22
 */
@Service
@RequiredArgsConstructor
public class SecurityUserService {

    private final MailProperties mailProperties;
    private final EmailService emailService;
    private final EventUserDao eventUserDao;
    private final AccountUserDao accountUserDao;
    @Value("${security-uri}")
    private String securityPath;

    public void createDeleteProfile(UserDto userDto) throws Exception {

        AccountUser accountUser = accountUserDao.findByUsername(userDto.getUsername()).orElseThrow();

        EventUser eventDelete = createEventUser(TypeEvent.DELETE_PROFILE, accountUser);
        EventUser eventReset = eventUserDao.save(createEventUser(TypeEvent.RESET_PASSWORD, accountUser));
        EmailContext contextMessage = createContextMessage(TypeEvent.DELETE_PROFILE, accountUser);
        try {
            contextMessage.addContext("ref_event", mailProperties.getBaseUrl() + securityPath + "/event/" + eventDelete.getGuidEvent());
            contextMessage.addContext("ref_reset", mailProperties.getBaseUrl() + securityPath + "/event/" + eventReset.getGuidEvent());
            emailService.sendMail(contextMessage);
        } catch (Exception e) {
            eventUserDao.delete(eventDelete);
            eventUserDao.delete(eventReset);
            throw new Exception(e);
        }

    }

    public void resetPassword(UserDto userDto, String newPassword) {

        AccountUser accountUser = accountUserDao.findByUsername(userDto.getUsername()).orElseThrow();
        accountUser.setPassword(newPassword);
        accountUserDao.save(accountUser);

    }

    public void resetEmail(UserDto userDto, String newEmail) {
        AccountUser accountUser = accountUserDao.findByUsername(userDto.getUsername()).orElseThrow();
        accountUser.setEmail(newEmail);
        accountUserDao.save(accountUser);
    }

    public void confirmAccount(UserDto userDto) {

    }

    public void deleteProfile(UserDto userDto) {
        AccountUser accountUser = accountUserDao.findByUsername(userDto.getUsername()).orElseThrow();
        accountUserDao.delete(accountUser);
    }

    private EmailContext createContextMessage(TypeEvent typeEvent, AccountUser accountUser) {
        EmailContext emailContext = EmailContext.builder()
                .from(mailProperties.getUsername())
                .to(accountUser.getEmail())
                .fromDisplayName(mailProperties.getDisplayFrom())
                .pathTemplate(mailProperties.getPathTemplate())
                .subject(typeEvent.getTitleEvent())
                .build();

        emailContext.addContext("type_event", TypeEvent.DELETE_PROFILE);
        emailContext.addContext("username", accountUser.getUsername());
        return emailContext;
    }

    private EventUser createEventUser(TypeEvent typeEvent, AccountUser accountUser) {

        EventUser eventUser = new EventUser();
        eventUser.setUserId(accountUser.getId());
        eventUser.setTypeEvent(typeEvent);
        return eventUserDao.save(eventUser);
    }


}
