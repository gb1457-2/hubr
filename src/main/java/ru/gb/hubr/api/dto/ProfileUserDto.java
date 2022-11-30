package ru.gb.hubr.api.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
