package ru.practicum.shareit.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ItemDaoStorage {
    int setId();

    HashMap<Integer, Item> getItems();

    List<Item> getListItems();

    Item createItem(Item item);

    Optional<Item> findItemById(Integer id);

    Item updateItem(Item item);
}
