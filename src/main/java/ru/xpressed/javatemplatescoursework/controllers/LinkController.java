package ru.xpressed.javatemplatescoursework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.xpressed.javatemplatescoursework.models.User;
import ru.xpressed.javatemplatescoursework.models.Order;
import ru.xpressed.javatemplatescoursework.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@Controller
@Slf4j
public class LinkController {
    final UserService userService;

    @Autowired
    public LinkController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/link")
    public String showOrderPage(HttpServletRequest request, Model model) {
        model.addAttribute("order", new Order());

        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "Выйти");
        model.addAttribute("linkInOrAccount", "/account");
        model.addAttribute("textInOrAccount", request.getUserPrincipal().getName());

        model.addAttribute("confirm", "false");
        model.addAttribute("another", "true");

        return "link";
    }

    @PostMapping("/link")
    public String completeOrder(@Valid Order order, BindingResult bindingResult, Model model, HttpServletRequest request) {
        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "Выйти");
        model.addAttribute("linkInOrAccount", "/account");
        model.addAttribute("textInOrAccount", request.getUserPrincipal().getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("confirm", "false");
            model.addAttribute("another", "true");
            model.addAttribute(order);
            return "link";
        }

        User user = userService.findByUsername(request.getUserPrincipal().getName());
        order.setDeparture(new Date());
        order.setUser(user);

        user.orders.add(order);
        userService.save(user);

        model.addAttribute("confirm", "true");
        model.addAttribute("another", "false");
        model.addAttribute("message", "Order confirmed! Courier will be in an hour.");
        return "link";
    }
}
