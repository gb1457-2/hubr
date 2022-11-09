package ru.gb.hubr.api.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserDto {
    @JsonIgnore
    private Long id;
    @NotBlank
    @Size(min = 3, message = "login length must be greater than 2 symbols")
    private String username;
    @NotNull(message = "is required")
    @Size(min = 8, message = "required 8 symbols")
    private String password;
    @NotNull(message = "is required")
    @Size(min = 8, message = "required 8 symbols")
    private String matchingPassword;
    @NotBlank(message = "is required")
    private String firstname;
    @NotBlank(message = "is required")
    private String lastname;
    @Email
    @NotBlank(message = "is required")
    private String email;
    @NotBlank(message = "is required")
    @Size(min = 5, message = "min 5 symbols")
    private String phone;

    @PastOrPresent
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate lockedAt;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate lockedUntil;

}
