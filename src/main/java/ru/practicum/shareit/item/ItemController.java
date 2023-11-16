package ru.practicum.shareit.item;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.item.service.ItemService;

/**
 * TODO Sprint add-controllers.
 */

@Slf4j
@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable Integer id) {
        log.info("Get request /items, item id: {}", id);
        return itemService.getItemById(id);
    }

    @GetMapping
    public List<ItemDto> getAllItemsByOwnerId(
            @RequestHeader("X-Sharer-User-Id") int id) {
        log.info("Get request /items, owner id: {}", id);
        return itemService.getAllItemsByOwnerId(id);
    }

    @GetMapping("/search")
    public List<ItemDto> searchAllItemsByOwnerId(
            @RequestParam(value = "text") String text) {
        if (text.isEmpty()) return new ArrayList<>();

        log.info("Get search items, request: {}", text);
        return itemService.searchItems(text);
    }

    @PostMapping
    public ItemDto createItem(
            @RequestHeader("X-Sharer-User-Id") int id,
            @Valid @RequestBody ItemDto itemDto) {
        log.info("Post request /items, data transmitted: {}", itemDto);
        return itemService.createItem(id, itemDto);
    }

    @PatchMapping("/{idItem}")
    public ItemDto updateItem(
            @PathVariable Integer idItem,
            @RequestHeader("X-Sharer-User-Id") int idOwner,
            @Valid @RequestBody ItemDtoUpdate itemDto) {
        log.info("Patch request /items data transmitted: {}", itemDto);
        ItemDto itemDto1 = itemService.updateItem(idItem, idOwner, itemDto);
        log.info("Patch DATA: {}", itemDto1);

        return itemDto1;
    }
}
