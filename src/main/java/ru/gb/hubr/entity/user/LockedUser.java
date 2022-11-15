package ru.gb.hubr.entity.user;

import org.springframework.security.core.GrantedAuthority;
import ru.gb.hubr.config.SecurityProperties;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vitaly Krivobokov
 * @Date 14.11.2022
 */
public class LockedUser extends AbstractAccountUserDecorator {

    private final SecurityProperties securityProperties;

    public LockedUser(AccountUser accountUser, SecurityProperties securityProperties) {
        super(accountUser);
        this.securityProperties = securityProperties;
    }

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = super.getAuthorities();
        return authorities.stream()
                .filter(grantedAuthority -> !this.accountUser.nowLocked() || !securityProperties.getLockedAuthorities().contains(grantedAuthority.getAuthority()))
                .collect(Collectors.toSet());
    }
}
