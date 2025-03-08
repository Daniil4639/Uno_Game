package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/uno")
public class PagesController {

    @GetMapping("/rooms")
    public String returnRoomsPage() {
        return "rooms";
    }

    @GetMapping("/game")
    public String returnGamePage(Model model, @RequestParam("room_id") Long id) {
        model.addAttribute("room_name", id);

        return "game";
    }
}