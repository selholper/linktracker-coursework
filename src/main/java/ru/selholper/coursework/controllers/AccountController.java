package ru.selholper.coursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.selholper.coursework.models.User;
import ru.selholper.coursework.models.enums.LinkType;
import ru.selholper.coursework.services.LinkService;
import ru.selholper.coursework.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AccountController {

    private static final Map<LinkType, String> LINK_TYPE_STRING_MAP_TO_RUSSIAN = Map.of(
            LinkType.BLOG, "Блог",
            LinkType.WORK, "Работа",
            LinkType.OTHER, "Другое",
            LinkType.STUDY, "Учеба",
            LinkType.ARTICLE, "Статья",
            LinkType.COMPANIES, "Компания",
            LinkType.ENTERTAINMENT, "Развлечения",
            LinkType.SOCIAL_MEDIA, "Соцсеть"
    );
    private final UserService userService;
    private final LinkService linkService;


    private void addAttributes(HttpServletRequest request, Model model) {
        model.addAttribute("username", request.getUserPrincipal().getName());
        model.addAttribute("linkOutOrUp", "/logout");
        model.addAttribute("textOutOrUp", "Выйти");
        model.addAttribute("linkInOrAccount", "/account");
        model.addAttribute("textInOrAccount", request.getUserPrincipal().getName());
        model.addAttribute("welcome", "С возвращением, "
                + request.getUserPrincipal().getName() + ", мы рады вас видеть!");
        model.addAttribute("translateLinkType", LINK_TYPE_STRING_MAP_TO_RUSSIAN);
    }

    @Autowired
    public AccountController(UserService userService, LinkService linkService) {
        this.userService = userService;
        this.linkService = linkService;
    }

    @GetMapping("/account")
    public String showAccountPage(HttpServletRequest request, Model model) {
        addAttributes(request, model);
        User user = userService.findByUsername(request.getUserPrincipal().getName());
        model.addAttribute("links", user.getLinks());

        return "account";
    }

    @GetMapping("/account/delete/{id}")
    public String deleteOrder(@PathVariable int id, HttpServletRequest request, Model model) {
        addAttributes(request, model);
        User user = userService.findByUsername(request.getUserPrincipal().getName());
        if (linkService.findById(id).isPresent()) {
            user.links.remove(linkService.getById(id));
            linkService.deleteById(id);
        }
        userService.save(user);
        model.addAttribute("links", user.getLinks());

        return "redirect:/account";
    }

    @GetMapping("/account/filter")
    public String filterLinks(@RequestParam String type, HttpServletRequest request, Model model) {
        addAttributes(request, model);
        User user = userService.findByUsername(request.getUserPrincipal().getName());
        model.addAttribute("links", user.getLinks()
                .stream()
                .filter((link) ->
                    LINK_TYPE_STRING_MAP_TO_RUSSIAN .get(link.getType()).equals(type)
                ).toList()
        );

        return "account";
    }
}
