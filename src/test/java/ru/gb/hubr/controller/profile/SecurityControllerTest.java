package ru.gb.hubr.controller.profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import ru.gb.hubr.AbstractTest;
import ru.gb.hubr.api.user.profile.ProfileService;
import ru.gb.hubr.api.user.ProfileUserDto;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class SecurityControllerTest extends AbstractTest {

    ProfileUserDto profileUserDto;

    @Mock
    ProfileService profileService;

    @BeforeEach
    void setUp() {
        profileUserDto = ProfileUserDto.builder()
                .email("vffsdf")
                .firstname("dfsdf")
                .lastname("dfsdf")
                .password("dfsdf")
                .phone("dfsdfsdf")
                .username("username")
                .build();
        given(profileService.findByUsername(anyString())).willReturn(profileUserDto);
    }


    @Test
    @Order(1)
    void testGetProfilePage() throws Exception {

        mockMvc.perform(get("/profile/security"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(model().attribute("user",
                        hasProperty("login",is(profileUserDto.getUsername()))
                    )
                )
                .andExpect(view().name("profile/security-form"));

    }

    @Test
    @Order(2)
    void testSaveSecurityPage() throws Exception {

        mockMvc.perform(post("/profile/security")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(profileUserDto)))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/profile/security"));

    }

    @Test
    @Order(2)
    void testDeleteProfile() throws Exception {

        mockMvc.perform(get("/profile/security/deleteProfile"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("showDeleteMessage",true))
                .andExpect(model().attribute("user",
                        hasProperty("login",is(profileUserDto.getUsername()))
                        )
                )
                .andExpect(view().name("profile/security-form"));

    }
}