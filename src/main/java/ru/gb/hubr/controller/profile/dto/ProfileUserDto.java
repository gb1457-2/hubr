package ru.gb.hubr.controller.profile.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.gb.hubr.api.security.UserDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProfileUserDto extends UserDto {

    @NotNull(message = "is required")
    @Size(min = 8, message = "required 8 symbols")
    private String newPassword;

    @NotNull(message = "is required")
    @Size(min = 8, message = "required 8 symbols")
    private String newMatchingPassword;

    @NotNull(message = "is required")
    @Size(min = 8, message = "required 8 symbols")
    private String newEmail;

}
