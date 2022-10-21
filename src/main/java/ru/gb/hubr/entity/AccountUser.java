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
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.MERGE)
    private Set<Article> articles;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "email")
    private String email;



}