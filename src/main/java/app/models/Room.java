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
    private List<String> users;

    private Long userNumber;

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
        this.users = new ArrayList<>();

        this.userNumber = 0L;
    }

    public void incrementUsers(String user) {
        users.add(user);
        userNumber += 1;
    }

    public void decrementUsers(String user) {
        users.remove(user);
    }

    public boolean isSuchNameAlreadyExists(String name) {
        return users.stream().anyMatch(user -> user.equals(name));
    }
}