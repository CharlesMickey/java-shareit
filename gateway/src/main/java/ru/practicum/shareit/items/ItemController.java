package ru.practicum.shareit.items;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.HttpConstants;
import ru.practicum.shareit.items.commetDto.CommentDto;
import ru.practicum.shareit.items.itemDto.ItemDto;
import ru.practicum.shareit.validated.Create;
import ru.practicum.shareit.validated.Update;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<Object> getAllItemsByOwnerId(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long id,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Get request /items, owner id: {}", id);
        return itemClient.getAllItemsByOwnerId(id, from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public ResponseEntity<Object> searchAllItemsByOwnerId(
            @RequestParam(value = "text") String text,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Get search items, request: {}", text);
        return itemClient.searchItems(text, from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @PathVariable Long id) {
        log.info("Get request /items, item id: {}", id);
        return itemClient.getItemById(id, userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createItem(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long id,
            @Validated(Create.class) @RequestBody ItemDto itemDto) {
        log.info("Post request /items, data transmitted: {}", itemDto);
        return itemClient.createItem(id, itemDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{idItem}")
    public ResponseEntity<Object> updateItem(
            @PathVariable Long idItem,
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long idOwner,
            @Validated(Update.class) @RequestBody ItemDto itemDto) {
        log.info("Patch request /items data transmitted: {}", itemDto);
        return itemClient.updateItem(idItem, idOwner, itemDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long id,
            @PathVariable Long itemId,
            @Validated(Create.class) @RequestBody CommentDto commentDto) {
        log.info("Post request /items/{}/comment, data transmitted: {}", itemId, commentDto);
        return itemClient.createComment(id, itemId, commentDto);
    }
}
