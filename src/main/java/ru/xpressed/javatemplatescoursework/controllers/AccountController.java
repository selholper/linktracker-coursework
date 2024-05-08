package ru.xpressed.javatemplatescoursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.xpressed.javatemplatescoursework.models.User;
import ru.xpressed.javatemplatescoursework.models.Order;
import ru.xpressed.javatemplatescoursework.services.UserService;
import ru.xpressed.javatemplatescoursework.services.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AccountController {
    final UserService userService;
    final OrderService orderService;

    @Autowired
    public AccountController(UserService userService, OrderService orderRepository) {
        this.userService = userService;
        this.orderService = orderRepository;
    }

    @GetMapping("/account")
    public String showAccountPage(HttpServletRequest request, Model model) {
        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "Выйти");
        model.addAttribute("linkInOrAccount", "/account");
        model.addAttribute("textInOrAccount", request.getUserPrincipal().getName());
        model.addAttribute("welcome", "Welcome back, " + request.getUserPrincipal().getName() + "! How's your day?");
        model.addAttribute("date", new SimpleDateFormat("dd.MM.y").format(new Date()));

        User user = userService.findByUsername(request.getUserPrincipal().getName());

        for (Order order : user.orders) {
            if (new Date().compareTo(order.getArrivalDate()) >= 0) {
                order.setBtn(1);
            } else {
                order.setBtn(0);
            }
        }

        model.addAttribute("orders", user.getOrders());

        return "account";
    }

    @GetMapping("/account/{id}")
    public String deleteOrder(@PathVariable int id, HttpServletRequest request, Model model) {
        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "Выйти");
        model.addAttribute("linkInOrAccount", "/account");
        model.addAttribute("textInOrAccount", request.getUserPrincipal().getName());
        model.addAttribute("welcome", "Welcome back, " + request.getUserPrincipal().getName() + "! How's your day?");
        model.addAttribute("date", new SimpleDateFormat("dd.MM.y").format(new Date()));

        User user = userService.findByUsername(request.getUserPrincipal().getName());
        if (orderService.findById(id).isPresent()) {
            user.orders.remove(orderService.getById(id));
            orderService.deleteById(id);
        }
        userService.save(user);

        for (Order order : user.orders) {
            if (new Date().compareTo(order.getArrivalDate()) >= 0) {
                order.setBtn(1);
            } else {
                order.setBtn(0);
            }
        }

        model.addAttribute("orders", user.getOrders());

        return "account";
    }
}
