package ru.gb.hubr.controller.profile;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.service.AccountUserService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final AccountUserService accountUserService;

    @GetMapping
    public String profilePage(@AuthenticationPrincipal UserDetails user, Model model) {
        UserDto byLogin = accountUserService.findByUsername(user.getUsername());
        model.addAttribute("user", byLogin);
        return "profile/profile-form";
    }


    @GetMapping("/edit")
    public String editProfile(@AuthenticationPrincipal UserDetails user, Model model) {
        model.addAttribute("isEdit", true);
        UserDto byLogin = accountUserService.findByUsername(user.getUsername());
        byLogin.setPassword("");
        model.addAttribute("user", byLogin);
        return "profile/profile-form";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String saveProfile(@RequestBody UserDto userDto) {
        accountUserService.save(userDto);
        return "redirect:/profile";
    }
}
