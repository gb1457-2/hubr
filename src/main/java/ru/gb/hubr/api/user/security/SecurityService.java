package ru.gb.hubr.api.user.security;

import ru.gb.hubr.api.user.UserDto;

public interface SecurityService {

    void deleteProfile(final UserDto userDto,String tokenDelete);

    void createDeleteProfile(final UserDto userDto) throws Exception;

    void resetPassword(final UserDto userDto,String newPassword);

    void resetEmail(final UserDto userDto,String newEmail);

}
