package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.booking.BookingNextLastDto;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(source = "item.request.id", target = "requestId")
    ItemDto toItemDto(Item item);

    @Mapping(source = "item.id", target = "id")
    ItemWithBookingsDateDto toItemWithBookingDto(Item item,
                                                 BookingNextLastDto lastBooking,
                                                 BookingNextLastDto nextBooking, List<CommentDto> comments);

    List<ItemDto> toItemDto(List<Item> listItems);

    @Mapping( target = "id", ignore = true)
    @Mapping(source = "itemDto.name", target = "name")
    Item toItem(User owner, ItemDto itemDto);


}
