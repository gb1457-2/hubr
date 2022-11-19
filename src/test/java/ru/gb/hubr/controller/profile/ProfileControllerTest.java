package ru.gb.hubr.controller.profile;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import ru.gb.hubr.AbstractTest;
import ru.gb.hubr.api.mapper.UserMapper;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.security.AccountRoleDao;
import ru.gb.hubr.entity.security.AccountRole;
import ru.gb.hubr.entity.security.Authority;

import javax.servlet.Filter;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.gb.hubr.controller.profile.TestConstants.BLOCKED_USER;
import static ru.gb.hubr.controller.profile.TestConstants.PROFILE_USER;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest extends AbstractTest {

    @Autowired
    AccountUserDao accountUserDao;

    @Autowired
    AccountRoleDao accountRoleDao;

    @Autowired
    UserMapper userMapper;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private WebApplicationContext context;

    @Test
    @Order(0)
    public void setUp() {


        AccountRole role = getRole();
        accountRoleDao.save(role);
        PROFILE_USER.setRoles(Set.of(role));
        accountUserDao.save(PROFILE_USER);
        BLOCKED_USER.setRoles(Set.of(role));
        accountUserDao.save(BLOCKED_USER);
    }


    @Test
    @WithUserDetails("username")
    @Order(1)
    void saveBlockedUser() throws Exception {
        mockMvc.perform(post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userMapper.toUserDto(PROFILE_USER))))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/profile"));
    }

    @Test
    @WithMockUser("username")
    @Order(2)
    void testGetProfilePage() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(model().attribute("user",
                        hasProperty("username", is(PROFILE_USER.getUsername()))))
                .andExpect(view().name("profile/profile-form"));
    }

    @Test
    @WithUserDetails("username")
    @Order(3)
    void testGetAnotherProfile() throws Exception {

        mockMvc.perform(get("/profile").param("username", "blocked_user"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(model().attribute("user",
                        hasProperty("username", is(BLOCKED_USER.getUsername()))))
                .andExpect(view().name("profile/profile-form"));

    }


    @Test
    @WithUserDetails("username")
    @Order(4)
    void testGetEditPage() throws Exception {
        mockMvc.perform(get("/profile/edit"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id")))
                .andExpect(model().attribute("isEdit", true))
                .andExpect(model().attribute("user",
                        hasProperty("username", is(PROFILE_USER.getUsername()))))
                .andExpect(view().name("profile/profile-form"));
    }

    @Test
    @Order(5)
    void testAccessArticleUser() throws Exception {
        RequestBuilder request = get("/articles/add")
                .with(user("sd")
                        .authorities(Authority.builder().permission("article.write").build()));
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    @WithUserDetails("blocked_user")
    void testDeniedAccessArticleUser() throws Exception {
        mockMvc.perform(get("/articles/add")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    private AccountRole getRole() {

        Set<Authority> build = Set.of(
                Authority.builder()
                        .id(1L)
                        .permission("article.write")
                        .build());
        ;
        return AccountRole.builder().authorities(build).id(1L).name("ROLE_USER").build();
    }


}