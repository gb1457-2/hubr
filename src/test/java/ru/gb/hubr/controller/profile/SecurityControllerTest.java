package ru.gb.hubr.controller.profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ru.gb.hubr.AbstractTest;
import ru.gb.hubr.api.user.ProfileUserDto;
import ru.gb.hubr.api.user.profile.ProfileService;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class SecurityControllerTest extends AbstractTest {

    ProfileUserDto profileUserDto;

    @MockBean
    ProfileService profileService;

    @BeforeEach
    void setUp() {
        profileUserDto = ProfileUserDto.builder()
                .email("vffsdf")
                .firstName("dfsdf")
                .lastName("dfsdf")
                .password("dfsdf")
                .phone("dfsdfsdf")
                .username("username")
                .build();
        given(profileService.getCurrentUser(any())).willReturn(profileUserDto);
    }


    @Test
    @Order(1)
    void testGetProfilePage() throws Exception {

        mockMvc.perform(get("/profile/security"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(model().attribute("user",
                        hasProperty("username", is(profileUserDto.getUsername()))))
                .andExpect(view().name("profile/security-form"));

    }

    @Test
    @Order(2)
    @Disabled
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
                .andExpect(model().attribute("user",
                        hasProperty("username", is(profileUserDto.getUsername()))))
                .andExpect(view().name("profile/security-form"));
    }
}