package ru.gb.hubr.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.gb.hubr.entity.Article;
import ru.gb.hubr.entity.common.InfoEntity;
import ru.gb.hubr.entity.security.AccountRole;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    @Column(name = "login",unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private Set<Article> articles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private Set<CommentNotification> commentNotifications;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private Set<ArticleNotification> articleNotifications;

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

    public boolean nowLocked(){
        if (lockedAt==null|| lockedUntil==null){
            return false;
        }
        return LocalDateTime.now().isAfter(lockedAt) && LocalDateTime.now().isBefore(lockedUntil);

    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<AccountRole> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void updateInfoByDuplicate(AccountUser anotherUser){
        this.firstName = anotherUser.getFirstName() == null ? this.firstName: anotherUser.getFirstName();
        this.lastName = anotherUser.getLastName() == null ? this.lastName: anotherUser.getEmail();
        this.articles = anotherUser.getArticles() == null ? this.articles: anotherUser.getArticles();
        this.lockedAt = anotherUser.getLockedAt() == null ? this.lockedAt: anotherUser.getLockedAt();
        this.lockedUntil = anotherUser.getLockedUntil() == null ? this.lockedUntil: anotherUser.getLockedUntil();
        this.email = anotherUser.getEmail() == null ? this.email: anotherUser.getEmail();
        this.phone = anotherUser.getPhone() == null ? this.phone: anotherUser.getEmail();
    }

}