package ru.practicum.shareit.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

@Component
public class InMemoryItemStorage implements ItemDaoStorage<Item> {

    private final HashMap<Integer, Item> items = new HashMap<>();
    private int id = 0;

    public int setId() {
        id++;
        return id;
    }

    public HashMap<Integer, Item> getItems() {
        return items;
    }

    public List<Item> getListItems() {
        List<Item> itemsList = new ArrayList<>(items.values());
        return itemsList;
    }

    public Optional<Item> findItemById(Integer id) {
        Item item = items.get(id);
        if (item != null) {
            return Optional.of(item);
        } else {
            return Optional.empty();
        }
    }

    public Item createItem(Item item) {
        item.setId(setId());
        items.put(item.getId(), item);
        return item;
    }

    public Item updateItem(Item item) {
        items.put(item.getId(), item);
        return item;
    }
}
