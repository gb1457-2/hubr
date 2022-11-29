package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.service.AccountUserService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserAdminController {

    private final AccountUserService accountUserService;

    @GetMapping("/all")
    public String getUserList(Model model) {
        model.addAttribute("users", accountUserService.findAll());
        return "admin/admin-users";
    }


    @GetMapping("/{userId}")
    public String getProfilePageById(Model model, @PathVariable("userId") Long id) {
        UserDto userDto;
        if (id != null) {
            userDto = accountUserService.findById(id);
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
            userDto = accountUserService.findById(id);
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

    @PutMapping("/unban")
    public String unbanUser(@RequestParam(name = "id") Long id) {
        UserDto userDto;
        if(id != null) {
                userDto = accountUserService.findById(id);
                if(userDto.isLocked() || userDto.getLockedAt() != null || userDto.getLockedUntil() != null) {
                userDto.setLocked(false);
                userDto.setLockedAt(null);
                userDto.setLockedUntil(null);
                accountUserService.save(userDto);
            }
        }
        return "redirect:admin/users/all";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(name = "id") Long id) {
     //   UserDto userDto = profileServiceSQL.findById(id);
        return "redirect:admin/users/all";
    }



}
