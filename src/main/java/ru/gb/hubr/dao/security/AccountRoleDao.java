package ru.gb.hubr.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.security.AccountRole;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {
}
