package ru.gb.hubr.controller.profile;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.gb.hubr.AbstractTest;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.service.mail.EmailService;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.gb.hubr.controller.profile.TestConstants.BLOCKED_USER;
import static ru.gb.hubr.controller.profile.TestConstants.PROFILE_USER;

class SecurityControllerTest extends AbstractTest {


    @Autowired
    AccountUserDao accountUserDao;

    @MockBean
    EmailService emailService;

    @Test
    @Order(0)
    public void setUp(){
        accountUserDao.save(PROFILE_USER);
        accountUserDao.save(BLOCKED_USER);
    }

    @Test
    @WithUserDetails("username")
    @Order(1)
    void testSaveProfile() throws Exception {
        mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PROFILE_USER)))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/profile"));
    }

    @Test
    @WithUserDetails("username")
    @Order(2)
    void testGetProfileSecurityPage() throws Exception {
        mockMvc.perform(get("/profile/security"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(model().attribute("user",
                        hasProperty("username", is(PROFILE_USER.getUsername()))))
                .andExpect(view().name("profile/security-form"));
    }

    @Test
    @WithUserDetails("username")
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