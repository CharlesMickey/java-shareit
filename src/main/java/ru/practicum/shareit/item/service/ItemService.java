package ru.practicum.shareit.item.service;

import java.util.List;

import ru.practicum.shareit.item.dto.ItemDto;


public interface ItemService {


    ItemDto getItemById(Integer id);

    List<ItemDto> getAllItemsByOwnerId(Integer id);

    List<ItemDto> searchItems(String text);

    ItemDto createItem(Integer id, ItemDto itemDto);

    ItemDto updateItem(
            Integer idItem,
            Integer idOwner,
            ItemDto itemDto
    );
}
