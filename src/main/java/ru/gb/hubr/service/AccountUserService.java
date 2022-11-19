package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.api.mapper.UserMapper;
import ru.gb.hubr.config.SecurityProperties;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.entity.user.AccountUser;
import ru.gb.hubr.entity.user.LockedUser;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
@Primary
@RequiredArgsConstructor
public class AccountUserService implements UserDetailsService {

    private final AccountUserDao accountUserDao;
    private final UserMapper userMapper;
    private final SecurityProperties securityProperties;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AccountUser accountUser = accountUserDao.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username: " + username + " not found"));
        if (accountUser.nowLocked()) {
            return new LockedUser(accountUser, securityProperties);
        }
        return accountUser;
    }

    @Transactional(readOnly = true)
    public UserDto findByUsername(String username) {
        AccountUser accountUser = accountUserDao.findByUsername(username).orElse(new AccountUser());

        UserDto userDto = userMapper.toUserDto(accountUser);
        return userDto;

    }

    public UserDto findById(Long idUser) {
        AccountUser accountUser = accountUserDao.findById(idUser).orElseThrow();
        UserDto userDto = userMapper.toUserDto(accountUser);

        return userDto;
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        if (accountUser.getUsername() != null) {
            accountUserDao.findByUsername(accountUser.getUsername())
                    .ifPresent((p) -> {accountUser.setVersion(p.getVersion());
                                        accountUser.setId(p.getId());});
        }
        return userMapper.toUserDto(accountUserDao.save(accountUser));
    }

    @Transactional
    public UserDto updateInfo(HttpSession session, UserDto userDto) {
        if (userDto.getUsername() == null) {
            throw new NullPointerException("Не найден пользователь");
        }
        ;
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        AccountUser accountUserDB = accountUserDao.findByUsername(userDto.getUsername()).orElseThrow();
        accountUserDB.updateInfoByDuplicate(accountUser);

        updateCurrentUser(session,accountUserDB);

        return userMapper.toUserDto(accountUserDao.save(accountUserDB));
    }

    private void updateCurrentUser(HttpSession session,AccountUser accountUser){
        SecurityContext context = (SecurityContext) session.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = context.getAuthentication();
        if (authentication.getPrincipal() instanceof AccountUser) {
            AccountUser currentUser = (AccountUser) authentication.getPrincipal();
            if (currentUser.getUsername().equals(accountUser.getUsername())) {
                currentUser.updateInfoByDuplicate(accountUser);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return accountUserDao.findAll().stream()
                .map(userMapper::toUserDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getCurrentUser(HttpSession session) {
        SecurityContext context = (SecurityContext) session.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = context.getAuthentication();

        Object principal = authentication.getPrincipal();

        if (principal instanceof AccountUser) {
            return userMapper.toUserDto((AccountUser) principal);
        }
        User currentUser = (User) authentication.getPrincipal();
        return findByUsername(currentUser.getUsername());
    }


}
