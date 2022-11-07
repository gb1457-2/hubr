package ru.gb.hubr.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.hubr.entity.security.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
