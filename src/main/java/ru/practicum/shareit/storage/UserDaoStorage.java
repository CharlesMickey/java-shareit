package ru.practicum.shareit.storage;

import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface UserDaoStorage {
        int setId();

    HashMap<Integer, User> getItems();

    List<User> getListItems();

    User createItem(User item);

    User updateItem(User item);

    Optional<User> findItemById(Integer id);

    void deleteItem(Integer id);
}
