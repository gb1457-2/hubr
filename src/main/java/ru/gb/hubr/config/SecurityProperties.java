package ru.gb.hubr.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Vitaly Krivobokov
 * @Date 14.11.2022
 */

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "security")
@RequiredArgsConstructor
public class SecurityProperties {
    private List<String> LockedAuthorities;
}
