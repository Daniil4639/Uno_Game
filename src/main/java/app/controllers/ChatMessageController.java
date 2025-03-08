package app.controllers;

import app.models.ChatMessage;
import app.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final RoomService roomService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage processMessage(@DestinationVariable Long roomId, ChatMessage message) {
        switch (message.getType()) {
            case ADD -> {
                roomService.addUser(roomId, message.getSender());
                message.setContent(message.getSender().getName() + " joined!");
                return message;
            }
            case DELETE -> {
                roomService.deleteUser(roomId, message.getSender());
                message.setContent(message.getSender().getName() + " leaved!");
                return message;
            }
            case SEND -> {
                return message;
            }
            default -> {
                throw new RuntimeException("Incorrect message Type!");
            }
        }
    }
}