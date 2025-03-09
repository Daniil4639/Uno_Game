package app.configs;

import app.models.ChatMessage;
import app.services.RoomService;
import app.tools.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;
    private final RoomService service;

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        Long room_id = (Long) headerAccessor.getSessionAttributes().get("room_id");

        if (username != null && room_id != null) {
            ChatMessage message = new ChatMessage(username, MessageType.DELETE,
                    username + " left!");

            messageTemplate.convertAndSend("/topic/" + room_id, message);

            service.deleteUser(room_id, username);
        }
        else {
            throw new RuntimeException("No session attributes: username=" + username +
                    ", room_id=" + room_id + "!");
        }
    }
}