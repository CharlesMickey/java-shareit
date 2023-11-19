package ru.practicum.shareit.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

@Component
public class InMemoryUserStorage implements UserDaoStorage {

    private int id = 0;
    private final HashMap<Integer, User> users = new HashMap<>();

    public int setId() {
        id++;
        return id;
    }

    public HashMap<Integer, User> getItems() {
        return users;
    }

    public List<User> getListItems() {
        List<User> usersList = new ArrayList<>(users.values());
        return usersList;
    }

    public Optional<User> findItemById(Integer id) {
        User user = users.get(id);
        if (user != null) {
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public User createItem(User user) {
        user.setId(setId());
        users.put(user.getId(), user);
        return user;
    }

    public User updateItem(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteItem(Integer id) {
        users.remove(id);
    }
}
