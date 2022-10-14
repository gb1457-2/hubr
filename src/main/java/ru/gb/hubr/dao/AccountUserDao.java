package ru.gb.hubr.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.AccountUser;

public interface AccountUserDao extends JpaRepository<AccountUser, Long> {
}