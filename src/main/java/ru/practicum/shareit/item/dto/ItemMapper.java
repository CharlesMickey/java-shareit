package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;


public class ItemMapper {
    public static ItemDto toUserDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.isAvailable(),
                item.getOwner() != null ? item.getOwner().getId() : null,
                item.getRequest() != null ? item.getRequest().getId() : null
        );
    }

}
