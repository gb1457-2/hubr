package ru.gb.hubr.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.hubr.api.dto.AccountRoleDto;
import ru.gb.hubr.api.mapper.AccountRoleMapper;
import ru.gb.hubr.dao.security.AccountRoleDao;
import ru.gb.hubr.entity.security.AccountRole;


@Service
@RequiredArgsConstructor
public class AccountRoleService {

    private final AccountRoleDao accountRoleDao;

    private final AccountRoleMapper accountRoleMapper;

    public AccountRoleDto getRoleDtoByName(String name) {
        AccountRole accountRole = accountRoleDao.findByName(name).orElse(null);
        return accountRoleMapper.toAccountRoleDto(accountRole);
    }

}
