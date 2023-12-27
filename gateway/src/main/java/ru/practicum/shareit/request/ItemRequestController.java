package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.HttpConstants;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.validated.Create;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createItemRequest(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @RequestBody @Validated(Create.class) ItemRequestDto itemRequestDto) {

        log.info("Post request /requests, user id: {}; itemRequestDto: {}", userId, itemRequestDto);

        return itemRequestClient.createItemRequestDto(userId, itemRequestDto);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsRequest(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId) {

        log.info("Get request /requests, user id: {}", userId);

        return itemRequestClient.getItemsRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequest(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @PathVariable Long requestId) {

        log.info("Get request /requests/{}, user ownerId: {}", requestId, userId);

        return itemRequestClient.getItemRequests(userId, requestId);
    }


    @GetMapping("all")
    public ResponseEntity<Object> getAllItemRequests(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {

        log.info("Get request /requests/all, userId: {}, from: {}, size: {}", userId, from, size);

        return  itemRequestClient.getAllItemRequests(userId, from, size);

    }
}
