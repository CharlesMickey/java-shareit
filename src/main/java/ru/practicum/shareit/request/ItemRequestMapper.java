package ru.practicum.shareit.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemRequestMapper {

    ItemRequestMapper INSTANCE = Mappers.getMapper(ItemRequestMapper.class);

    ItemRequestDto toItemRequestDto(ItemRequest itemRequest);

    ItemRequestDto toItemRequestDto(ItemRequest itemRequest, List<ItemDto> items);

    ItemRequestDto toItemRequestDtoWithItems(ItemRequest itemRequest, List<ItemDto> items);

    List<ItemRequestDto> toItemRequestDto(List<ItemRequest> itemRequests);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "user", target = "requestor")
    ItemRequest toItemRequest(User user, ItemRequestDto itemRequestDto);
}
