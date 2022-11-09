package ru.gb.hubr.entity;

import lombok.*;
import ru.gb.hubr.entity.common.InfoEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account_user")
public class AccountUser extends InfoEntity {

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

}