package ru.practicum.shareit.request;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto createItemRequestDto(Long userId, ItemRequestDto itemRequestDto);

    List<ItemRequestDto> getItemsRequests(Long userId);

    ItemRequestDto getItemRequests(Long userId, Long requestId);

    List<ItemRequestDto> getAllItemRequests(Long userId, Integer from, Integer size);
}
