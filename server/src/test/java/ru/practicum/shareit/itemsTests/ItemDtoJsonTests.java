package ru.practicum.shareit.itemsTests;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingsDateDto;


@JsonTest
public class ItemDtoJsonTests {

    @Autowired
    JacksonTester<ItemDto> jsonItemDto;

    @Autowired
    JacksonTester<ItemWithBookingsDateDto> jsonItemWithBookingsDateDto;

    private ItemDto itemDto;
    private ItemWithBookingsDateDto itemWithBookingsDateDto;

    @BeforeEach
    void setUp() {
        itemDto = ItemDto.builder()
                .id(1L)
                .name("item")
                .available(true)
                .description("description")
                .build();

        itemWithBookingsDateDto = ItemWithBookingsDateDto.builder()
                .id(1L)
                .name("item")
                .available(false)
                .description("description")
                .build();
    }

    @Test
    void testItemDtoId() throws Exception {
        JsonContent<ItemDto> result = jsonItemDto.write(itemDto);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
    }

    @Test
    void testItemDtoName() throws Exception {
        JsonContent<ItemDto> result = jsonItemDto.write(itemDto);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
    }

    @Test
    void testItemDtoDescription() throws Exception {
        JsonContent<ItemDto> result = jsonItemDto.write(itemDto);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
    }

    @Test
    void testItemDtoAvailable() throws Exception {
        JsonContent<ItemDto> result = jsonItemDto.write(itemDto);
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
    }

    @Test
    void testItemWithBookingsDateDtoId() throws Exception {
        JsonContent<ItemWithBookingsDateDto> result = jsonItemWithBookingsDateDto.write(itemWithBookingsDateDto);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
    }

    @Test
    void testItemWithBookingsDateDtoName() throws Exception {
        JsonContent<ItemWithBookingsDateDto> result = jsonItemWithBookingsDateDto.write(itemWithBookingsDateDto);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
    }

    @Test
    void testItemWithBookingsDateDtoDescription() throws Exception {
        JsonContent<ItemWithBookingsDateDto> result = jsonItemWithBookingsDateDto.write(itemWithBookingsDateDto);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
    }

    @Test
    void testItemWithBookingsDateDtoAvailable() throws Exception {
        JsonContent<ItemWithBookingsDateDto> result = jsonItemWithBookingsDateDto.write(itemWithBookingsDateDto);
        assertThat(result).extractingJsonPathBooleanValue("$.available").isFalse();
    }
}

