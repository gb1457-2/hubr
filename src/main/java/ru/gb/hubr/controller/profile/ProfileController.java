package ru.gb.hubr.controller.profile;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.api.security.UserDto;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @GetMapping("")
    public String profilePage(Model model) {
        UserDto user = UserDto.builder()
                .email("vffsdf")
                .firstname("dfsdf")
                .lastname("dfsdf")
                .phone("dfsdfsdf")
                .build();

        model.addAttribute("user",user);
        return "profile/profile-form";
    }

    @GetMapping("/edit")
    public String editProfile(Model model) {
        UserDto user = UserDto.builder()
                .email("vffsdf")
                .firstname("dfsdf")
                .lastname("dfsdf")
                .phone("dfsdfsdf")
                .build();

        model.addAttribute("isEdit",true);
        model.addAttribute("user",user);
        return "profile/profile-form";
    }

    @PostMapping("")
    public String saveProfile(UserDto userDto) {

        System.out.println(userDto.getEmail());
        return "redirect:/profile";
    }


}
