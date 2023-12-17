package ru.practicum.shareit.itemsTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemWithBookingsDateDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class ItemMapperTests {

    @Autowired
    private ItemMapper itemMapper;

    @Test
    void toItemWithNullInputShouldReturnNull() {
        ItemWithBookingsDateDto itemWithBookingsDateDto = itemMapper
                .toItemWithBookingDto(null, null, null, null);

        assertNull(itemWithBookingsDateDto);
    }

    @Test
    void toItemDtoWithNullInputShouldReturnNull() {
        ItemDto itemDto = itemMapper.toItemDto((Item) null);

        assertNull(itemDto);
    }

    @Test
    void toItemDtoListWithNullInputShouldReturnNull() {
        List<ItemDto> itemDtos = itemMapper.toItemDto((List<Item>) null);

        assertNull(itemDtos);
    }

    @Test
    void toItemWithNullUserAndItemDtoShouldReturnNull() {
        Item item = itemMapper.toItem(null, null);

        assertNull(item);
    }

}
