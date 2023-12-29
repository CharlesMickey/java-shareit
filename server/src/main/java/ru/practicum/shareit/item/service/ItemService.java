package ru.practicum.shareit.item.service;

import java.util.List;

import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingsDateDto;


public interface ItemService {


    ItemWithBookingsDateDto getItemById(Long id, Long userId);

    List<ItemWithBookingsDateDto> getAllItemsByOwnerId(Long id, Integer from, Integer size);

    List<ItemDto> searchItems(String text, Integer from, Integer size);

    ItemDto createItem(Long id, ItemDto itemDto);

    ItemDto updateItem(
            Long idItem,
            Long idOwner,
            ItemDto itemDto
    );

    CommentDto createComment(Long userId, Long idItem, CommentDto commentDto);
}
