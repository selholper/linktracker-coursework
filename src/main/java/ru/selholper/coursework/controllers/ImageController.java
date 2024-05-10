package ru.selholper.coursework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.selholper.coursework.services.LinkService;

@Controller
public class ImageController {

    private final LinkService linkService;

    @Autowired
    public ImageController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable int id) {
        return linkService.findById(id).isPresent()
                ? linkService.findById(id).get().getImageData()
                : null;
    }
}
