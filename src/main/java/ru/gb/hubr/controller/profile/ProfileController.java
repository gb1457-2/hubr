package ru.gb.hubr.controller.profile;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.api.user.profile.ProfileService;
import ru.gb.hubr.entity.AccountUser;
import ru.gb.hubr.service.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final UserMapper userMapper;

    @GetMapping("")
    public String profilePage(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("user", userMapper.toUserDto((AccountUser) user));

        return "profile/profile-form";
    }

    @GetMapping("/edit")
    public String editProfile(@AuthenticationPrincipal UserDetails user,Model model) {
        model.addAttribute("isEdit", true);
        UserDto byLogin = userMapper.toUserDto((AccountUser) user);
        byLogin.setPassword("");
        model.addAttribute("user", byLogin);
        return "profile/profile-form";
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String saveProfile(UserDto userDto) {
        profileService.save(userDto);
        return "redirect:/profile";
    }





}
