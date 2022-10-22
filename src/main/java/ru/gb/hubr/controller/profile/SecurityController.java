package ru.gb.hubr.controller.profile;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.api.user.profile.ProfileService;
import ru.gb.hubr.api.user.profile.ProfileUserDto;
import ru.gb.hubr.api.user.security.SecurityService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile/security")
public class SecurityController {

    private final ProfileService profileService;
    private final SecurityService securityService;

    @GetMapping("")
    public String profilePage(Model model, HttpSession session) {
        model.addAttribute("user", profileService.getCurrentUser(session));
        return "profile/security-form";
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String updateSecurity(ProfileUserDto profileUserDto) {
        return "redirect:/profile/security";
    }


    @GetMapping("/deleteProfile")
    public String sendDeleteProfile(HttpSession session, Model model) throws Exception {
        UserDto currentUser = profileService.getCurrentUser(session);
        securityService.createDeleteProfile(currentUser);
        model.addAttribute("user", currentUser);
        return "profile/security-form";
    }

    @GetMapping("/deleteProfile/{tokenDelete}")
    public String sendDeleteProfile(HttpSession session, @PathVariable(name = "tokenDelete") String tokenDelete, Model model) {
        UserDto currentUser = profileService.getCurrentUser(session);
        model.addAttribute("typeBackdrop", "Подтверждение удаления");
        return "profile/security-form";
    }

    @PostMapping("/deleteProfile/{tokenDelete}")
    public String sendDeleteProfile(HttpSession session, UserDto userDto, @PathVariable(name = "tokenDelete") String tokenDelete, Model model) {
        UserDto currentUser = profileService.getCurrentUser(session);
        boolean successfulDelete = false;
        if (currentUser.getPassword().equals(userDto.getPassword())){
            securityService.deleteProfile(currentUser,tokenDelete);
            successfulDelete = true;
        } else {
            return  "redirect:/deleteProfile/"+tokenDelete;
        }

        return "";
    }
}
