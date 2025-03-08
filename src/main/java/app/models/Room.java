package app.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Room {

    private Long id;
    private String name;
    private List<User> users;

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();
    }

    public void incrementUsers(User user) {
        users.add(user);
    }

    public void decrementUsers(User user) {
        users.remove(user);
    }

    public boolean isSuchNameAlreadyExists(String name) {
        return users.stream().anyMatch(user -> user.getName().equals(name));
    }
}