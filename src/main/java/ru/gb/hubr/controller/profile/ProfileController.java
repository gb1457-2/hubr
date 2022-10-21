package ru.gb.hubr.controller.profile;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.api.user.profile.ProfileService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("")
    public String profilePage(HttpSession session,Model model) {
        model.addAttribute("user", profileService.getCurrentUser(session));
        return "profile/profile-form";
    }

    @GetMapping("/edit")
    public String editProfile(HttpSession session,Model model) {
        model.addAttribute("isEdit", true);
        UserDto byLogin = profileService.getCurrentUser(session);
        byLogin.setPassword("");
        model.addAttribute("user", byLogin);
        return "profile/profile-form";
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String saveProfile(UserDto userDto) {
        System.out.println(userDto.getEmail());
        return "redirect:/profile";
    }


}
