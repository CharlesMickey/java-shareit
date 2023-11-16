package ru.practicum.shareit.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface UserDaoStorage<T> {
    int setId();

    HashMap<Integer, T> getItems();

    List<T> getListItems();

    T createItem(T item);

    T updateItem(T item);

    Optional<T> findItemById(Integer id);

    void deleteItem(Integer id);
}
