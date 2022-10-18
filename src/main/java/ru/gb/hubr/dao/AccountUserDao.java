package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.AccountUser;

import java.util.Optional;

public interface AccountUserDao extends JpaRepository<AccountUser, Long> {

    Optional<AccountUser> findAccountUserByLogin(String login);
}