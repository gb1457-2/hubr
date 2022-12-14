package ru.gb.hubr.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.security.AccountRole;
import ru.gb.hubr.entity.user.AccountUser;

import java.util.Optional;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {

    Optional<AccountRole> findByName(String name);

}
