package ru.practicum.shareit.itemRequestTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestDto;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ItemRequestMapperTests {

    @Autowired
    private ItemRequestMapper itemRequestMapper;

    @Test
    void toItemRequestDtoShouldMapCorrectly() {
        ItemRequest itemRequest = new ItemRequest(1L, "Description", new User(), LocalDateTime.now());

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto(itemRequest);

        assertEquals(1L, itemRequestDto.getId());
        assertEquals("Description", itemRequestDto.getDescription());
        assertNotNull(itemRequestDto.getRequestor());
        assertNotNull(itemRequestDto.getCreated());
    }

    @Test
    void toItemRequestDtoWithItemsShouldMapCorrectly() {
        ItemRequest itemRequest = new ItemRequest(1L, "Description", new User(), LocalDateTime.now());
        List<ItemDto> items = new ArrayList<>();

        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDtoWithItems(itemRequest, items);

        assertEquals(1L, itemRequestDto.getId());
        assertEquals("Description", itemRequestDto.getDescription());
        assertNotNull(itemRequestDto.getRequestor());
        assertNotNull(itemRequestDto.getCreated());
        assertTrue(itemRequestDto.getItems().isEmpty());
    }

    @Test
    void toItemRequestDtoWithNullInputShouldReturnNull() {
        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDto((ItemRequest) null);

        assertNull(itemRequestDto);
    }

    @Test
    void toItemRequestDtoWithItemsAndNullInputShouldReturnNull() {
        ItemRequestDto itemRequestDto = itemRequestMapper.toItemRequestDtoWithItems(null, null);

        assertNull(itemRequestDto);
    }

    @Test
    void toItemRequestDtoListShouldMapCorrectly() {
        ItemRequest itemRequest1 = new ItemRequest(1L, "Description1", new User(), LocalDateTime.now());
        ItemRequest itemRequest2 = new ItemRequest(2L, "Description2", new User(), LocalDateTime.now());

        List<ItemRequestDto> itemRequestDtos = itemRequestMapper.toItemRequestDto(List.of(itemRequest1, itemRequest2));

        assertEquals(2, itemRequestDtos.size());
        assertEquals(1L, itemRequestDtos.get(0).getId());
        assertEquals("Description1", itemRequestDtos.get(0).getDescription());
        assertNotNull(itemRequestDtos.get(0).getRequestor());
        assertNotNull(itemRequestDtos.get(0).getCreated());
        assertEquals(2L, itemRequestDtos.get(1).getId());
        assertEquals("Description2", itemRequestDtos.get(1).getDescription());
        assertNotNull(itemRequestDtos.get(1).getRequestor());
        assertNotNull(itemRequestDtos.get(1).getCreated());
    }

    @Test
    void toItemRequestWithNullInputShouldReturnNull() {
        ItemRequest itemRequest = itemRequestMapper.toItemRequest(null, null);

        assertNull(itemRequest);
    }

    @Test
    void toItemRequestWithNullItemRequestDtoShouldMapOtherFieldsCorrectly() {
        User user = new User();

        ItemRequest itemRequest = itemRequestMapper.toItemRequest(user, null);

        assertNull(itemRequest.getDescription());
        assertNotNull(itemRequest.getRequestor());
    }

}

