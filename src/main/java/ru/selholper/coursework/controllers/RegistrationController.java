package ru.selholper.coursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.selholper.coursework.configurations.SecurityConfig;
import ru.selholper.coursework.models.User;
import ru.selholper.coursework.services.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final SecurityConfig securityConfig;

    @Autowired
    public RegistrationController(UserService userService, SecurityConfig securityConfig) {
        this.userService = userService;
        this.securityConfig = securityConfig;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(User user, Model model) {
        model.addAttribute("customer", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String completeRegistration(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customer", user);
            return "registration";
        }

        if (!user.getPassword().equals(user.getRepeated())) {
            model.addAttribute("reperr", "Введенные пароли не совпадают!");
            return "registration";
        }

        if (userService.findByUsername(user.getUsername()) == null) {
            user.setPassword(securityConfig.encoder().encode(user.getPassword()));
            userService.save(user);
            model.addAttribute("message", "Регистрация выполнена");
            model.addAttribute("onload", "redirectTimer()");
        } else {
            bindingResult.rejectValue("username", "customer.username", "Логин "
                    + user.getUsername() +  " уже занят");
        }

        return "registration";
    }
}
