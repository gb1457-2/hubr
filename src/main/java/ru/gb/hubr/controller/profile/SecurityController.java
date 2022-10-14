package ru.gb.hubr.controller.profile;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.gb.hubr.controller.profile.dto.ProfileUserDto;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile/security")
public class SecurityController {

    @GetMapping("")
    public String profilePage(Model model) {
        ProfileUserDto user = ProfileUserDto.builder()
                .email("vffsdf")
                .firstname("dfsdf")
                .lastname("dfsdf")
                .password("dfsdf")
                .phone("dfsdfsdf")
                .login("username")
                .build();

        model.addAttribute("profileUserDto",user);
        return "profile/security-form";
    }

    @PostMapping("")
    public String updateSecurity( ProfileUserDto profileUserDto) {
        System.out.println(profileUserDto);
        return "redirect:/profile/security";
    }


    @GetMapping("/deleteProfile")
    public String sendDeleteProfile(ProfileUserDto profileUserDto) {

        System.out.println(profileUserDto);
        return "redirect:/profile/security";
    }
}
