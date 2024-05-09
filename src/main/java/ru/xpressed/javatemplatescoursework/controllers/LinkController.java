package ru.xpressed.javatemplatescoursework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.xpressed.javatemplatescoursework.models.Link;
import ru.xpressed.javatemplatescoursework.models.User;
import ru.xpressed.javatemplatescoursework.services.UserService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@Slf4j
public class LinkController {
    final UserService userService;

    @Autowired
    public LinkController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/link")
    public String showLinkPage(HttpServletRequest request, Model model) {
        model.addAttribute("link", new Link());

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
    public String completeOrder(@Valid Link link, BindingResult bindingResult, Model model, HttpServletRequest request) {
        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "Выйти");
        model.addAttribute("linkInOrAccount", "/account");
        model.addAttribute("textInOrAccount", request.getUserPrincipal().getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("confirm", "false");
            model.addAttribute("another", "true");
            model.addAttribute(link);
            return "link";
        }

        if (link.getDescription() == null || link.getDescription().isBlank()) {
            link.setDescription("Описание не предоставлено");
        }
        if (link.getImageData() == null || link.getImageData().length == 0) {
            File imagePath = new File("src/main/resources/static/icons/logo2.png");
            try {
                link.setImageData(Files.readAllBytes(imagePath.getAbsoluteFile().toPath()));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
            log.info("Set default favicon for link {}", link.getLink());
        }

        User user = userService.findByUsername(request.getUserPrincipal().getName());
        link.setUser(user);
        user.links.add(link);
        userService.save(user);

        model.addAttribute("confirm", "true");
        model.addAttribute("another", "false");
        model.addAttribute("message", "Ссылка успешно добавлена");

        return "link";
    }

}
