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
    public AccountUser findByUsername(String username) {
        return accountUserDao.findByUsername(username).orElse(new AccountUser());
    }

    public UserDto findDtoByUsername(String username) {
        return userMapper.toUserDto(findByUsername(username));
    }

    public AccountUser findById(Long userId) {
        return accountUserDao.findById(userId).orElseThrow();
    }

    public UserDto findDtoById(Long userId) {
        return userMapper.toUserDto(findById(userId));
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        AccountUser accountUser = userMapper.toAccountUser(userDto);
        if (accountUser.getUsername() != null) {
            accountUserDao.findByUsername(accountUser.getUsername())
                    .ifPresent((p) -> {
                        accountUser.setVersion(p.getVersion());
                        accountUser.setId(p.getId());
                    });
        }
        return userMapper.toUserDto(accountUserDao.save(accountUser));
    }

    @Transactional
    public UserDto updateInfo(HttpSession session, UserDto userDto) {
        if (userDto.getUsername() == null) {
            throw new NullPointerException("Не найден пользователь");
        }

        AccountUser accountUser = userMapper.toAccountUser(userDto);
        AccountUser accountUserDB = accountUserDao.findByUsername(userDto.getUsername()).orElseThrow();
        accountUserDB.updateInfoByDuplicate(accountUser);

        updateCurrentUser(session, accountUserDB);

        return userMapper.toUserDto(accountUserDao.save(accountUserDB));
    }

    private void updateCurrentUser(HttpSession session, AccountUser accountUser) {
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
    public UserDto getCurrentUserDto(HttpSession session) {
        return userMapper.toUserDto(getCurrentUser(session));
    }

    @Transactional(readOnly = true)
    public AccountUser getCurrentUser(HttpSession session) {
        Object principal = getPrincipal(session);

        if (principal instanceof AccountUser) {
            return (AccountUser) principal;
        }
        User currentUser = (User) principal;
        return findByUsername(currentUser.getUsername());
    }


    public String getCurrentUsername(HttpSession session) {
        Object principal = getPrincipal(session);
        if (principal instanceof AccountUser) {
            return ((AccountUser) principal).getUsername();
        }
        User currentUser = (User) principal;
        return currentUser.getUsername();
    }

    private Object getPrincipal(HttpSession session) {
        SecurityContext context = (SecurityContext) session.getAttribute(SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication = context.getAuthentication();
        return authentication.getPrincipal();
    }

}
