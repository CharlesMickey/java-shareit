package ru.practicum.shareit.itemsTests;

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

    @Test
    void testItemDto() throws Exception {
        ItemDto itemDto = ItemDto
                .builder()
                .id(1L)
                .name("item")
                .available(true)
                .description("description")
                .build();

        JsonContent<ItemDto> result = jsonItemDto.write(itemDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isTrue();
    }

    @Test
    void testItemWithBookingsDateDto() throws Exception {
        ItemWithBookingsDateDto itemWithBookingsDateDto = ItemWithBookingsDateDto
                .builder()
                .id(1L)
                .name("item")
                .available(false)
                .description("description")
                .build();

        JsonContent<ItemWithBookingsDateDto> result = jsonItemWithBookingsDateDto.write(itemWithBookingsDateDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("item");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathBooleanValue("$.available").isFalse();
    }

}
