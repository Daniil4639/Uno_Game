package app.controllers;

import app.dto.RoomDTO;
import app.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomsConnectionController {

    private final RoomService roomService;

    @GetMapping()
    public List<RoomDTO> getRooms() {
        return roomService.getRooms()
                .stream()
                .map(RoomDTO::new)
                .toList();
    }

    @GetMapping("/{roomId}")
    public RoomDTO getRoom(@PathVariable("roomId") Long roomId) {
        return new RoomDTO(roomService.getRoom(roomId));
    }

    @GetMapping("/{roomId}/check-name")
    public String checkUserNameInRoom(@RequestParam("name") String name,
                                      @PathVariable("roomId") Long roomId) {

        roomService.checkNameInRoom(roomId, name);
        return name;
    }

    @PostMapping()
    public RoomDTO createRoom(@RequestBody String name) {

        System.out.println(name);
        return new RoomDTO(roomService.createRoom(name));
    }
}