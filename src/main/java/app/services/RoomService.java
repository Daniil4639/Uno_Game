package app.services;

import app.exceptions.SuchNameAlreadyExists;
import app.models.Room;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    private final Map<Long, Room> rooms;
    private Long roomsNumber;

    public RoomService() {
        rooms = new HashMap<>() {};
        roomsNumber = 0L;
    }

    public Collection<Room> getRooms() {
        return rooms.values();
    }

    public Room getRoom(Long roomId) {
        return rooms.get(roomId);
    }

    public void checkNameInRoom(Long roomId, String name) {
        if (rooms.get(roomId).isSuchNameAlreadyExists(name)) {
            throw new SuchNameAlreadyExists("Name '" + name + "' already exists in this room!");
        }
    }

    public Room createRoom(String name) {
        rooms.put(roomsNumber, new Room(roomsNumber, name));
        roomsNumber += 1;
        return rooms.get(roomsNumber - 1);
    }

    public void addUser(Long roomId, String user) {
        rooms.get(roomId).incrementUsers(user);
    }

    public void deleteUser(Long roomId, String user) {
        rooms.get(roomId).decrementUsers(user);

        if (rooms.get(roomId).getUsers().isEmpty()) {
            rooms.remove(roomId);
        }
    }
}