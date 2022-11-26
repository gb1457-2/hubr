package ru.gb.hubr.controller.profile;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.service.AccountUserService;

import javax.servlet.http.HttpSession;
import java.nio.file.AccessDeniedException;


/**
 * @author Vitaly Krivobokov
 * @Date 13.11.22
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final AccountUserService accountUserService;

    @GetMapping
    public String profilePage(HttpSession session, @RequestParam(required = false) String username, Model model) {
        UserDto byLogin = accountUserService.getCurrentUserDto(session);
        boolean isMyProfile = byLogin.getUsername().equals(username) || username == null;
        model.addAttribute("isMyProfile", isMyProfile);
        model.addAttribute("user", isMyProfile ? byLogin : accountUserService.findDtoByUsername(username));
        return "profile/profile-form";
    }


    @GetMapping("/edit")
    public String editProfile(HttpSession session, Model model) {
        model.addAttribute("isEdit", true);
        UserDto byLogin = accountUserService.getCurrentUserDto(session);
        byLogin.setPassword("");
        model.addAttribute("user", byLogin);
        return "profile/profile-form";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String save(HttpSession session, @RequestBody UserDto userDto) throws AccessDeniedException {
        UserDto byLogin = accountUserService.getCurrentUserDto(session);
        if (!byLogin.getUsername().equals(userDto.getUsername())){
            throw new AccessDeniedException("Записан другой пользователь");
        }
        UserDto updateUser = accountUserService.save(userDto);
        return "redirect:/profile";
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateProfile(HttpSession session, @RequestBody UserDto userDto, Model model)  {
        UserDto updateUser = accountUserService.updateInfo(session, userDto);
        model.addAttribute("user", updateUser);
        return "profile/profile-form";
    }

}
