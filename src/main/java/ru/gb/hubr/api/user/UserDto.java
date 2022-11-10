package ru.gb.hubr.api.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {
    @JsonIgnore
    private Long id;
    @NotBlank
    @Size(min = 3, message = "username length must be greater than 2 symbols")
    private String username;
    @NotNull(message = "is required")
    @Size(min = 8, message = "required 8 symbols")
    private String password;
    @NotNull(message = "is required")
    @Size(min = 8, message = "required 8 symbols")
    private String matchingPassword;
    @NotBlank(message = "is required")
    private String firstName;
    @NotBlank(message = "is required")
    private String lastName;
    @Email
    @NotBlank(message = "is required")
    private String email;
    @NotBlank(message = "is required")
    @Size(min = 5, message = "min 5 symbols")
    private String phone;


}
