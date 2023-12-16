package ru.practicum.shareit.item;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.HttpConstants;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingsDateDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.validated.Create;
import ru.practicum.shareit.validated.Update;


@Slf4j
@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ItemWithBookingsDateDto> getAllItemsByOwnerId(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long id,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Get request /items, owner id: {}", id);
        return itemService.getAllItemsByOwnerId(id, from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public List<ItemDto> searchAllItemsByOwnerId(
            @RequestParam(value = "text") String text,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (text.isBlank()) return new ArrayList<>();

        log.info("Get search items, request: {}", text);
        return itemService.searchItems(text, from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ItemWithBookingsDateDto getItemById(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @PathVariable Long id) {
        log.info("Get request /items, item id: {}", id);
        return itemService.getItemById(id, userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ItemDto createItem(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long id,
            @Validated(Create.class) @RequestBody ItemDto itemDto) {
        log.info("Post request /items, data transmitted: {}", itemDto);
        return itemService.createItem(id, itemDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{idItem}")
    public ItemDto updateItem(
            @PathVariable Long idItem,
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long idOwner,
            @Validated(Update.class) @RequestBody ItemDto itemDto) {
        log.info("Patch request /items data transmitted: {}", itemDto);
        ItemDto itemDto1 = itemService.updateItem(idItem, idOwner, itemDto);
        log.info("Patch DATA: {}", itemDto1);

        return itemDto1;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long id,
            @PathVariable Long itemId,
            @Validated(Create.class) @RequestBody CommentDto commentDto) {
        log.info("Post request /items/{}/comment, data transmitted: {}", itemId, commentDto);
        return itemService.createComment(id, itemId, commentDto);
    }
}
