package ru.gb.hubr.controller.profile;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.hubr.api.event.EventDto;
import ru.gb.hubr.api.event.EventService;
import ru.gb.hubr.api.user.ProfileUserDto;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.api.user.profile.ProfileService;
import ru.gb.hubr.api.user.security.SecurityService;
import ru.gb.hubr.entity.TypeEvent;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("${security-uri}")
public class SecurityController {

    private final ProfileService profileService;
    private final SecurityService securityService;
    private final EventService eventService;
    @Value("${security-uri}")
    private String pathSecurity;


    @GetMapping("")
    public String profilePage(Model model, HttpSession session) {
        model.addAttribute("user", profileService.getCurrentUser(session));
        return "profile/security-form";
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public String updateSecurity(ProfileUserDto profileUserDto) {
        return "redirect:"+pathSecurity;
    }


    @GetMapping("/deleteProfile")
    public String sendDeleteProfile(HttpSession session, Model model) throws Exception {
        UserDto currentUser = profileService.getCurrentUser(session);
        securityService.createDeleteProfile( currentUser);
        model.addAttribute("user", currentUser);
        return "profile/security-form";
    }

    @GetMapping("/{token}")
    public String sendDeleteProfile(HttpSession session,@PathVariable(name = "token") String token, Model model) {
        EventDto eventByToken = eventService.getEventByToken(token);
        UserDto currentUser = profileService.getCurrentUser(session);
        model.addAttribute("user", currentUser);
        model.addAttribute("typeBackdrop", TypeEvent.valueOf(eventByToken.getTypeEvent()));
        return "profile/security-form";
    }

    @PostMapping("/{token}")
    public String workByTokenRequest(UserDto userDto, @PathVariable(name = "token") String token, Model model) {
        EventDto eventByToken = eventService.getEventByToken(token);
        UserDto currentUser = profileService.findById(eventByToken.getUserId());
        TypeEvent typeEvent = TypeEvent.valueOf(eventByToken.getTypeEvent());

        boolean successfulDelete = false;
        if (typeEvent.equals(TypeEvent.RESET_EMAIL)) {
            securityService.resetEmail(currentUser, userDto.getEmail());
            successfulDelete = true;
        } else if (typeEvent.equals(TypeEvent.RESET_PASSWORD)) {
            securityService.resetPassword(currentUser, userDto.getPassword());
            successfulDelete = true;
        } else if (typeEvent.equals(TypeEvent.CONFIRM_ACCOUNT)) {
            securityService.resetPassword(currentUser, userDto.getPassword());
            successfulDelete = true;
        } else if (typeEvent.equals(TypeEvent.DELETE_PROFILE) && currentUser.getPassword().equals(userDto.getPassword())) {
            securityService.deleteProfile(currentUser);
            successfulDelete = true;
        }
        model.addAttribute("successfulDelete", successfulDelete);
        return "redirect:"+pathSecurity + token;
    }

}
