package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.HttpConstants;
import ru.practicum.shareit.validated.Create;

import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@Slf4j
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto createItemRequest(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @RequestBody @Validated(Create.class) ItemRequestDto itemRequestDto) {

        log.info("Post request /requests, user id: {}; itemRequestDto: {}", userId, itemRequestDto);

        return itemRequestService.createItemRequestDto(userId, itemRequestDto);
    }

    @GetMapping
    public List<ItemRequestDto> getItemsRequest(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId) {

        log.info("Get request /requests, user id: {}", userId);

        return itemRequestService.getItemsRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getItemRequest(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @PathVariable Long requestId) {

        log.info("Get request /requests/{}, user ownerId: {}", requestId, userId);

        return itemRequestService.getItemRequests(userId, requestId);
    }


    @GetMapping("all")
    public List<ItemRequestDto> getAllItemRequests(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        log.info("Get request /requests/all, userId: {}, from: {}, size: {}", userId, from, size);


        return  itemRequestService.getAllItemRequests(userId, from, size);

    }
}
