package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.hubr.api.dto.UserNotificationDto;
import ru.gb.hubr.service.UserNotificationService;

import java.time.LocalDateTime;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/user-notifications")
public class UserNotificationAdminController {

    private final UserNotificationService userNotificationService;

    @GetMapping("/all")
    public String getUserNotificationList(Model model) {
        model.addAttribute("userNotifications", userNotificationService.findAll());
        return "admin/admin-user-notifications";
    }

    @GetMapping("/delete")
    public String deleteOrRestoreUserNotification(@RequestParam(name = "id") Long id) {
        UserNotificationDto userNotificationDto;
        if(id != null) {
            userNotificationDto = userNotificationService.findUserNotificationById(id);
            if(userNotificationDto.getDeletedAt() != null) {
                userNotificationDto.setDeletedAt(null);
            } else {
                userNotificationDto.setRead(true);
                userNotificationDto.setDeletedAt(LocalDateTime.now());
            }
            userNotificationService.save(userNotificationDto);
        }
        return "redirect:/admin/user-notifications/all";
    }

}
