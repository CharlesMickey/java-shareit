package ru.practicum.shareit.item.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.booking.BookingNextLastDto;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ItemMapper {

    public ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }

    public ItemWithBookingsDateDto toItemWithBookingDto(Item item,
                                                        BookingNextLastDto lastBooking,
                                                        BookingNextLastDto nextBooking, List<CommentDto> comments) {
        return new ItemWithBookingsDateDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                lastBooking,
                nextBooking,
                comments
        );
    }

    public List<ItemDto> toItemDto(List<Item> listItems) {
        List<ItemDto> listItemsDto = new ArrayList<>();

        for (Item item : listItems) {
            listItemsDto.add(toItemDto(item));
        }

        return listItemsDto;
    }

    public Item toItem(User owner, ItemDto itemDto) {
        return new Item(
                itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable() == true ? true : false,
                owner,
                null
        );
    }
}
