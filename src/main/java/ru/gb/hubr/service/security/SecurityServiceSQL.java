package ru.gb.hubr.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.mail.EmailContext;
import ru.gb.hubr.api.mail.EmailService;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.api.user.security.SecurityService;
import ru.gb.hubr.config.MailProperties;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.EventUserDao;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.entity.EventUser;
import ru.gb.hubr.entity.TypeEvent;


@Service
@RequiredArgsConstructor
public class SecurityServiceSQL implements SecurityService {

    private final MailProperties mailProperties;
    private final EmailService emailService;
    private final EventUserDao eventUserDao;
    private final AccountUserDao accountUserDao;
    @Value("${security-uri}")
    private String securityPath;

    @Override
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

    @Override
    public void resetPassword(UserDto userDto, String newPassword) {

        AccountUser accountUser = accountUserDao.findByUsername(userDto.getUsername()).orElseThrow();
        accountUser.setPassword(newPassword);
        accountUserDao.save(accountUser);

    }

    @Override
    public void resetEmail(UserDto userDto, String newEmail) {
        AccountUser accountUser = accountUserDao.findByUsername(userDto.getUsername()).orElseThrow();
        accountUser.setEmail(newEmail);
        accountUserDao.save(accountUser);
    }

    @Override
    public void confirmAccount(UserDto userDto) {

    }

    @Override
    public void deleteProfile(UserDto userDto ) {
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
        emailContext.addContext("username", accountUser.getFirstName());
        return emailContext;
    }

    private EventUser createEventUser(TypeEvent typeEvent, AccountUser accountUser) {

        EventUser eventUser = new EventUser();
        eventUser.setUserId(accountUser.getId());
        eventUser.setTypeEvent(typeEvent);
        return eventUserDao.save(eventUser);
    }



}
