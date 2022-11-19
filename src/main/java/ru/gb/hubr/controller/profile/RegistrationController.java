package ru.gb.hubr.controller.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.hubr.dao.AccountUserDao;
import ru.gb.hubr.dao.security.AccountRoleDao;
import ru.gb.hubr.entity.user.AccountUser;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final AccountUserDao userRepo;
    private final AccountRoleDao roleRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("user", new AccountUser());
        return "registration";
    }

    // todo pezhe roleRepo.findById(2L).get() в коде непонятно что значит 2L, лучше добавить enum
    @PostMapping
    public String processRegistration(@RequestBody AccountUser user) {

        AccountUser savedUser = AccountUser
                .builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(roleRepo.findById(2L).get())
                .build();
        userRepo.save(savedUser);
        return "redirect:/login";
    }

}
