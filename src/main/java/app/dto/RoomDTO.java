package app.dto;

import app.models.Room;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDTO {

    private Long roomId;
    private String roomName;
    private Integer userCount;

    public RoomDTO(Room room) {
        this.roomId = room.getId();
        this.roomName = room.getName();
        this.userCount = room.getUsers().size();
    }
}