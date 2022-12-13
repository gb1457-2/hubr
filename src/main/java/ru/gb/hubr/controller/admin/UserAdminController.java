package ru.gb.hubr.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.hubr.api.dto.AccountRoleDto;
import ru.gb.hubr.api.dto.UserDto;
import ru.gb.hubr.api.dto.UserNotificationDto;
import ru.gb.hubr.enumeration.BanPeriod;
import ru.gb.hubr.enumeration.UserNotificationType;
import ru.gb.hubr.service.AccountRoleService;
import ru.gb.hubr.service.AccountUserService;
import ru.gb.hubr.service.UserNotificationService;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class UserAdminController {

    private final String ROLE_ADMIN = "ROLE_ADMIN";

    private final AccountUserService accountUserService;

    private final AccountRoleService accountRoleService;

    private final UserNotificationService userNotificationService;

    @GetMapping("/all")
    public String getUserList(Model model) {
        model.addAttribute("users", accountUserService.findAll());
        return "admin/admin-users";
    }


    @GetMapping("/{userId}")
    public String getProfilePageById(Model model, @PathVariable("userId") Long id) {
        UserDto userDto;
        if (id != null) {
            userDto = accountUserService.findDtoById(id);
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
            userDto = accountUserService.findDtoById(id);
        } else {
            return "redirect:/admin/users/all";
        }
        model.addAttribute("isEdit", true);
        model.addAttribute("user", userDto);
        return "profile/profile-form";
    }


    @GetMapping("/appoint-admin")
    public String grantAdminAuthorities(@RequestParam(name = "id") Long id, @AuthenticationPrincipal UserDetails user) {
        UserDto userDto;
        if(id != null) {
            userDto = accountUserService.findDtoById(id);
            if(!userDto.getRoleNames().contains(ROLE_ADMIN)) {
                if(userDto.getRoles() == null) {
                    userDto.setRoles(new HashSet<>());
                }
                AccountRoleDto accountRoleDto = accountRoleService.getRoleDtoByName(ROLE_ADMIN);
                if (accountRoleDto == null) {
                    accountRoleDto = new AccountRoleDto();
                    accountRoleDto.setName(ROLE_ADMIN);
                }
                userDto.getRoles().add(accountRoleDto);
                accountUserService.save(userDto);
                UserNotificationDto userNotificationDto = new UserNotificationDto();
                userNotificationDto.setUserNotificationType(UserNotificationType.ADMIN_APPOINTMENT);
                userNotificationDto.setMessage(UserNotificationType.ADMIN_APPOINTMENT.getMessage());
                userNotificationDto.setAuthor(user.getUsername());
                userNotificationDto.setRecipient(userDto.getUsername());
                userNotificationService.save(userNotificationDto);

            }
        }
        return "redirect:/admin/users/all";
    }

    @GetMapping("/dismiss-admin")
    public String provokeAdminAuthorities(@RequestParam(name = "id") Long id, @AuthenticationPrincipal UserDetails user) {
        UserDto userDto;
        if(id != null) {
            userDto = accountUserService.findDtoById(id);
                Set<AccountRoleDto> roles = userDto.getRoles();
                AccountRoleDto adminRole = null;
                if(roles != null && roles.size() > 0) {
                    for(AccountRoleDto accountRoleDto : roles) {
                        if(accountRoleDto.getName().equals(ROLE_ADMIN)) {
                            adminRole = accountRoleDto;
                        }
                    }
                    if(adminRole != null) {
                        roles.remove(adminRole);
                        accountUserService.save(userDto);
                        UserNotificationDto userNotificationDto = new UserNotificationDto();
                        userNotificationDto.setUserNotificationType(UserNotificationType.ADMIN_DISMISS);
                        userNotificationDto.setMessage(UserNotificationType.ADMIN_DISMISS.getMessage());
                        userNotificationDto.setAuthor(user.getUsername());
                        userNotificationDto.setRecipient(userDto.getUsername());
                        userNotificationService.save(userNotificationDto);
                    }
                }
            }
        return "redirect:/admin/users/all";
    }

    @GetMapping("/show-ban-form")
    public String showBanForm(Model model, @RequestParam(name = "id") Long id) {
        UserDto userDto;
        if(id != null) {
            userDto = accountUserService.findDtoById(id);
            if(!userDto.isLocked()) {
                model.addAttribute("periods", BanPeriod.values());
                model.addAttribute("user", userDto);
                return "admin/ban-form";
            }
        }
        return "redirect:/admin/users/all";
    }

    @PostMapping("/ban")
    public String banUser(@RequestParam(name = "id") Long id, HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        UserDto userDto;
        if(id != null) {
            userDto = accountUserService.findDtoById(id);
            String banPeriod = request.getParameter("banPeriod");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lockedUntil = null;
            switch (banPeriod) {
                case "ONE_WEEK":
                    lockedUntil = now.plusDays(7L);
                    break;
                case "TWO_WEEKS":
                    lockedUntil = now.plusDays(14L);
                    break;
                case "MONTH":
                    lockedUntil = now.plusMonths(1L);
                    break;
                case "FOREVER":
                    lockedUntil = now.plusYears(1000L);
                    break;
            }
            userDto.setLockedAt(now);
            userDto.setLockedUntil(lockedUntil);
            accountUserService.save(userDto);
            UserNotificationDto userNotificationDto = new UserNotificationDto();
            userNotificationDto.setUserNotificationType(UserNotificationType.BAN);
            userNotificationDto.setMessage(UserNotificationType.BAN.getMessage() + lockedUntil);
            userNotificationDto.setAuthor(user.getUsername());
            userNotificationDto.setRecipient(userDto.getUsername());
            userNotificationService.save(userNotificationDto);
        }

        return "redirect:/admin/users/all";
    }


    @GetMapping("/unban")
    public String unbanUser(@RequestParam(name = "id") Long id) {
        UserDto userDto;
        if(id != null) {
                userDto = accountUserService.findDtoById(id);
                if(userDto.isLocked()) {
                userDto.setLockedAt(null);
                userDto.setLockedUntil(null);
                userDto.setLocked(false);
                accountUserService.save(userDto);
            }
        }
        return "redirect:/admin/users/all";
    }


    @GetMapping("/delete")
    public String deleteOrRestoreUser(@RequestParam(name = "id") Long id) {
       UserDto userDto;
        if (id != null) {
            userDto = accountUserService.findDtoById(id);
            if(userDto.getDeletedAt() != null) {
                userDto.setDeletedAt(null);
            } else {
                userDto.setDeletedAt(LocalDateTime.now());
            }
            accountUserService.save(userDto);
        }
        return "redirect:/admin/users/all";
    }


}

