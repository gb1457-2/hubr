package ru.gb.hubr.controller.profile;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.hubr.api.dto.EventDto;
import ru.gb.hubr.api.dto.ProfileUserDto;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.entity.TypeEvent;
import ru.gb.hubr.service.AccountUserService;
import ru.gb.hubr.service.EventService;
import ru.gb.hubr.service.SecurityUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Vitaly Krivobokov
 * @Date 13.11.22
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("${security-uri}")
public class SecurityController {

    private final AccountUserService accountUserService;
    private final SecurityUserService securityUserService;
    private final EventService eventService;
    @Value("${security-uri}")
    private String pathSecurity;


    @ModelAttribute("baseUri")
    public String getInitializeMyObject() {
        return pathSecurity;
    }

    @GetMapping
    public String profilePage(Model model, HttpSession session) {
        model.addAttribute("user", accountUserService.getCurrentUser(session));
        return "profile/security-form";
    }


    //todo vitaly
    @PostMapping("/updatePassword")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(ProfileUserDto profileUserDto) {
        return "redirect:" + pathSecurity;
    }

    //todo vitaly
    @PostMapping("/updateEmail")
    @ResponseStatus(HttpStatus.OK)
    public String updateEmail(ProfileUserDto profileUserDto) {
        return "redirect:" + pathSecurity;
    }



    @GetMapping("/deleteProfile")
    public String sendDeleteProfile(HttpServletRequest request, HttpSession session, Model model) throws Exception {
        UserDto currentUser = accountUserService.getCurrentUser(session);
        securityUserService.createDeleteProfile(currentUser);
        model.addAttribute("user", currentUser);

        return "profile/security-form";
    }

    @GetMapping("/event/{token}")
    public String sendDeleteProfile(HttpSession session, @PathVariable(name = "token") String token, Model model) {
        EventDto eventByToken = eventService.getEventByToken(token);
        UserDto currentUser = accountUserService.getCurrentUser(session);
        model.addAttribute("user", currentUser);
        model.addAttribute("typeBackdrop", TypeEvent.valueOf(eventByToken.getTypeEvent()));
        return "profile/security-form";
    }

    //todo vitaly не доделаны методы отличные от удаления
    @PostMapping("/event/{token}")
    public String workByTokenRequest(UserDto userDto, @PathVariable(name = "token") String token, Model model) {
        EventDto eventByToken = eventService.getEventByToken(token);
        UserDto currentUser = accountUserService.findById(eventByToken.getUserId());
        TypeEvent typeEvent = TypeEvent.valueOf(eventByToken.getTypeEvent());

        boolean successfulDelete = false;
        if (typeEvent.equals(TypeEvent.RESET_EMAIL)) {
            securityUserService.resetEmail(currentUser, userDto.getEmail());
            successfulDelete = true;
        } else if (typeEvent.equals(TypeEvent.RESET_PASSWORD)) {
            securityUserService.resetPassword(currentUser, userDto.getPassword());
            successfulDelete = true;
        } else if (typeEvent.equals(TypeEvent.CONFIRM_ACCOUNT)) {
            securityUserService.resetPassword(currentUser, userDto.getPassword());
            successfulDelete = true;
        } else if (typeEvent.equals(TypeEvent.DELETE_PROFILE) && currentUser.getPassword().equals(userDto.getPassword())) {
            securityUserService.deleteProfile(currentUser);
            successfulDelete = true;
        }
        model.addAttribute("successfulDelete", successfulDelete);
        return "redirect:" + pathSecurity + token;
    }

}
