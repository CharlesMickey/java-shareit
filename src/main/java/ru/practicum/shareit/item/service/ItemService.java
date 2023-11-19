package ru.practicum.shareit.item.service;

import java.util.List;

import ru.practicum.shareit.item.dto.ItemDto;


public interface ItemService {


    public ItemDto getItemById(Integer id);

    public List<ItemDto> getAllItemsByOwnerId(Integer id);

    public List<ItemDto> searchItems(String text);

    public ItemDto createItem(Integer id, ItemDto itemDto);

    public ItemDto updateItem(
            Integer idItem,
            Integer idOwner,
            ItemDto itemDto
    );
}
