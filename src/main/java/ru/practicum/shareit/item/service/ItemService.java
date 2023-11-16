package ru.practicum.shareit.item.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.storage.ItemDaoStorage;
import ru.practicum.shareit.storage.UserDaoStorage;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemDaoStorage<Item> itemDao;
    private final UserDaoStorage<User> userDao;
    private final ItemMapper itemMapper;

    public ItemDto getItemById(Integer id) {
        Item item = itemDao
                .findItemById(id)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));

        return itemMapper.toItemDto(item);
    }

    public List<ItemDto> getAllItemsByOwnerId(Integer id) {
        User owner = userDao
                .findItemById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        return itemDao
                .getListItems()
                .stream()
                .filter(i -> i.getOwner().getId() == id)
                .map(i -> itemMapper.toItemDto(i))
                .collect(Collectors.toList());
    }

    public List<ItemDto> searchItems(String text) {
        return itemDao
                .getListItems()
                .stream()
                .filter(i ->
                        i.getAvailable() == true &&
                                (i.getName().toLowerCase().contains(text.toLowerCase()) ||
                                        i
                                                .getDescription()
                                                .toLowerCase()
                                                .contains(text.toLowerCase())))
                .map(i -> itemMapper.toItemDto(i))
                .collect(Collectors.toList());
    }

    public ItemDto createItem(Integer id, ItemDto itemDto) {
        User owner = userDao
                .findItemById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemMapper.toItem(owner, itemDto);

        itemDao.createItem(item);

        return itemMapper.toItemDto(item);
    }

    public ItemDto updateItem(
            Integer idItem,
            Integer idOwner,
            ItemDtoUpdate itemDto
    ) {
        userDao
                .findItemById(idOwner)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemDao
                .findItemById(idItem)
                .filter(itemFromStorage ->
                        itemFromStorage.getOwner() != null &&
                                itemFromStorage.getOwner().getId() == (idOwner))
                .orElseThrow(() -> new NotFoundException("Вещь не найдена или ошибка доступа"));

        item.setAvailable(
                itemDto.getAvailable() == null
                        ? item.getAvailable()
                        : itemDto.getAvailable()
        );
        item.setDescription(
                itemDto.getDescription() == null ||
                        itemDto.getDescription().isEmpty() ||
                        itemDto.getDescription().isBlank()
                        ? item.getDescription()
                        : itemDto.getDescription());
        item.setName(
                itemDto.getName() == null ||
                        itemDto.getName().isEmpty() ||
                        itemDto.getName().isBlank()
                        ? item.getName()
                        : itemDto.getName()
        );

        itemDao.updateItem(item);

        return itemMapper.toItemDto(item);
    }
}
