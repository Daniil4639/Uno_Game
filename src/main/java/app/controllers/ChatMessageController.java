package app.controllers;

import app.models.ChatMessage;
import app.services.RoomService;
import app.tools.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final RoomService roomService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage processMessage(@DestinationVariable Long roomId,
                                      @Payload ChatMessage message,
                                      SimpMessageHeaderAccessor headerAccessor) {

        if (message.getType().equals(MessageType.ADD)) {
            roomService.addUser(roomId, message.getSender());
            message.setContent(message.getSender() + " joined!");

            headerAccessor.getSessionAttributes().put("room_id", roomId);
            headerAccessor.getSessionAttributes().put("username", message.getSender());
        }

        return message;
    }
}