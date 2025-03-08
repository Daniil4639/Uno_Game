package app.controllers;

import app.models.ChatMessage;
import app.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final RoomService roomService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage processMessage(@DestinationVariable Long roomId,
                                      @Payload ChatMessage message) {

        switch (message.getType()) {
            case ADD -> {
                roomService.addUser(roomId, message.getSender());
                message.setContent(message.getSender().getName() + " joined!");
            }
            case DELETE -> {
                roomService.deleteUser(roomId, message.getSender());
                message.setContent(message.getSender().getName() + " leaved!");
            }
            case SEND -> {
            }
            default -> {
                throw new RuntimeException("Incorrect message Type!");
            }
        }

        return message;
    }
}