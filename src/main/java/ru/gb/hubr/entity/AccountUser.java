package ru.gb.hubr.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gb.hubr.entity.common.InfoEntity;
import ru.gb.hubr.entity.security.AccountRole;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account_user")
public class AccountUser extends InfoEntity implements UserDetails {

    @Column(name = "login")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private Set<Article> articles;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "email")
    private String email;


    @Column(name = "phone")
    private String phone;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Singular
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")})
    private Set<AccountRole> roles;

    @Builder.Default
    private boolean accountNonExpired = true;
    @Builder.Default
    private boolean accountNonLocked = true;
    @Builder.Default
    private boolean credentialsNonExpired = true;
    @Builder.Default
    private boolean enabled = true;

    public Set<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = this.roles.stream()
                .map(AccountRole::getAuthorities)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        authorities.addAll(mapRolesToAuthorities(this.roles));
        return authorities;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<AccountRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


}