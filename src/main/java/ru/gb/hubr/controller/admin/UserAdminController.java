package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.hubr.api.user.UserDto;
import ru.gb.hubr.service.profile.ProfileServiceSQL;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserAdminController {

    private final ProfileServiceSQL profileServiceSQL;

    @GetMapping("/all")
    public String getUserList(Model model) {
        model.addAttribute("users", profileServiceSQL.findAll());
        return "admin/admin-users";
    }


    @GetMapping("/{userId}")
    public String getProfilePageById(Model model, @PathVariable("userId") Long id) {
        UserDto userDto;
        if (id != null) {
            userDto = profileServiceSQL.findById(id);
        } else {
            return "redirect:/admin/users/all";
        }
        model.addAttribute("user", userDto);
        return "profile/profile-form";
    }

    @GetMapping("/edit")
    public String editProfileById(Model model, @RequestParam(name = "id") Long id) {
        UserDto userDto;
        if(id != null) {
            userDto = profileServiceSQL.findById(id);
        } else {
            return "redirect:/admin/users/all";
        }
        model.addAttribute("isEdit", true);
        model.addAttribute("user", userDto);
        return "profile/profile-form";

    }


    @PutMapping("/ban")
    public String banUser(@RequestParam(name = "id") Long id) {
      //  UserDto userDto = profileServiceSQL.findById(id);
        return "redirect:admin/users/all";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
     //   UserDto userDto = profileServiceSQL.findById(id);
        return "redirect:admin/users/all";
    }

}
