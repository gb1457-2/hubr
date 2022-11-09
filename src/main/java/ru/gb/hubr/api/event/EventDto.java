package ru.gb.hubr.api.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventDto {

    @NotBlank
    private UUID guidEvent;

    @NotBlank
    private Long userId;

    @NotBlank
    private LocalDateTime deletedAt;

    @NotBlank
    private int lifetimeSeconds;

    @NotBlank
    private String typeEvent;
}
