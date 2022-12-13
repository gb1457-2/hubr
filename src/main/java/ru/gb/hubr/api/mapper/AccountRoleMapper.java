package ru.gb.hubr.api.mapper;

import org.mapstruct.Mapper;
import ru.gb.hubr.api.dto.AccountRoleDto;
import ru.gb.hubr.entity.security.AccountRole;


@Mapper
public interface AccountRoleMapper {

    AccountRoleDto toAccountRoleDto(AccountRole accountRole);

    AccountRole toAccountRole(AccountRoleDto accountRoleDto);


}
