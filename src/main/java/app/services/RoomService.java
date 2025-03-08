package app.services;

import app.exceptions.SuchNameAlreadyExists;
import app.models.Room;
import app.models.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {

    private final Map<Long, Room> rooms;

    public RoomService() {
        rooms = new HashMap<>() {{
            Room room1 = new Room(1L, "First room");
            room1.getUsers().add(new User(1L, "Никита"));

            Room room2 = new Room(2L, "Second room");
            room2.getUsers().add(new User(2L, "Илья"));
            room2.getUsers().add(new User(3L, "Даниил"));

            put(1L, room1);
            put(2L, room2);
        }};
    }

    public Collection<Room> getRooms() {
        return rooms.values();
    }

    public void checkNameInRoom(Long roomId, String name) {
        if (rooms.get(roomId).isSuchNameAlreadyExists(name)) {
            throw new SuchNameAlreadyExists("Name '" + name + "' already exists in this room!");
        }
    }

    public Room createRoom(Long roomId, String name) {
        rooms.put(roomId, new Room(roomId, name));
        return rooms.get(roomId);
    }

    public void addUser(Long roomId, User user) {
        rooms.get(roomId).incrementUsers(user);
    }

    public void deleteUser(Long roomId, User user) {
        rooms.get(roomId).decrementUsers(user);
    }
}