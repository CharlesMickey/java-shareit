package ru.practicum.shareit.itemRequestTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.request.ItemRequestDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class ItemRequestDtoJsonTests {

    @Autowired
    JacksonTester<ItemRequestDto> json;

    private ItemRequestDto itemRequestDto;

    @BeforeEach
    void setUp() {
        itemRequestDto = ItemRequestDto.builder()
                .id(1L)
                .description("descriptionOfItemRequest")
                .created(LocalDateTime.of(2023, 12, 16, 19, 11))
                .build();
    }

    @Test
    void testItemRequestDtoId() throws Exception {
        JsonContent<ItemRequestDto> result = json.write(itemRequestDto);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
    }

    @Test
    void testItemRequestDtoDescription() throws Exception {
        JsonContent<ItemRequestDto> result = json.write(itemRequestDto);
        assertThat(result).extractingJsonPathStringValue("$.description")
                .isEqualTo("descriptionOfItemRequest");
    }

    @Test
    void testItemRequestDtoCreated() throws Exception {
        JsonContent<ItemRequestDto> result = json.write(itemRequestDto);
        assertThat(result).extractingJsonPathStringValue("$.created")
                .isEqualTo("2023-12-16T19:11:00");
    }
}
