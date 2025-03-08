package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/uno")
public class PagesController {

    @GetMapping("/rooms")
    public String returnRoomsPage() {
        return "rooms";
    }
}