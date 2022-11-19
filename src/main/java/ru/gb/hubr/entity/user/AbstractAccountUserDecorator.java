package ru.gb.hubr.entity.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * @author Vitaly Krivobokov
 * @Date 14.11.2022
 */
// добавил декоратор для нарашивания функционала безопасности пользователя
public abstract class AbstractAccountUserDecorator extends AccountUser {

    protected AccountUser accountUser;

    public AbstractAccountUserDecorator(AccountUser accountUser) {
        this.accountUser = accountUser;
        this.setFirstName(accountUser.getFirstName());
        this.setLastName(accountUser.getLastName());
        this.setEmail(accountUser.getEmail());
        this.setPhone(accountUser.getPhone());
        this.setLockedAt(accountUser.getLockedAt());
        this.setLockedUntil(accountUser.getLockedUntil());
        this.setCreatedAt(accountUser.getCreatedAt());
    }

    @Override
    public boolean nowLocked() {
        return accountUser.nowLocked();
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return accountUser.getAuthorities();
    }

    @Override
    public String getPassword() {
        return accountUser.getPassword();
    }

    @Override
    public String getUsername() {
        return accountUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountUser.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountUser.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return accountUser.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return accountUser.isEnabled();
    }

}
