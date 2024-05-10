package ru.selholper.coursework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping({"/index", "/", "/home"})
    public String showIndexPage(HttpServletRequest request, Model model) {
        if (request.getUserPrincipal() != null) {
            model.addAttribute("username", request.getUserPrincipal().getName());
            model.addAttribute("linkOutOrUp", "/logout");
            model.addAttribute("textOutOrUp", "Выйти");
            model.addAttribute("linkInOrAccount", "/account");
            model.addAttribute("textInOrAccount", request.getUserPrincipal().getName());
        } else {
            model.addAttribute("linkOutOrUp", "/registration");
            model.addAttribute("textOutOrUp", "Регистрация");
            model.addAttribute("linkInOrAccount", "/login");
            model.addAttribute("textInOrAccount", "Вход");
        }
        return "index";
    }
}
