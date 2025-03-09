package app.controllers;

import app.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/uno")
@RequiredArgsConstructor
public class PagesController {

    private final RoomService service;

    @GetMapping("/rooms")
    public String returnRoomsPage() {
        return "rooms";
    }

    @GetMapping("/game")
    public String returnGamePage(Model model, @RequestParam("room_id") Long room_id,
                                 @RequestParam("username") String username) {

        model.addAttribute("username", username);
        model.addAttribute("room_id", room_id);

        return "game";
    }
}