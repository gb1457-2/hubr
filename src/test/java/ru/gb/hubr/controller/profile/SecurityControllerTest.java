package ru.gb.hubr.controller.profile;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import ru.gb.hubr.AbstractTest;
import ru.gb.hubr.service.AccountUserService;
import ru.gb.hubr.service.mail.EmailService;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static ru.gb.hubr.controller.profile.TestConstants.PROFILE_USER;

class SecurityControllerTest extends AbstractTest {

    @Autowired
    AccountUserService accountUserService;

    @MockBean
    EmailService emailService;

    @Test
    @Order(1)
    void testSaveProfile() throws Exception {
        mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PROFILE_USER)))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/profile"));
    }

    @Test
    @WithMockUser(username = "username", password = "password", roles = "USER")
    @Order(2)
    void testGetProfilePage() throws Exception {
        mockMvc.perform(get("/profile/security"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(model().attribute("user",
                        hasProperty("username", is(PROFILE_USER.getUsername()))))
                .andExpect(view().name("profile/security-form"));
    }

    @Test
    @WithMockUser(username = "username", password = "password", roles = "USER")
    @Order(3)
    void testDeleteProfile() throws Exception {
        doNothing().when(emailService).sendMail(any());
        mockMvc.perform(get("/profile/security/deleteProfile"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user",
                        hasProperty("username", is(PROFILE_USER.getUsername()))))
                .andExpect(view().name("profile/security-form"));
    }
}