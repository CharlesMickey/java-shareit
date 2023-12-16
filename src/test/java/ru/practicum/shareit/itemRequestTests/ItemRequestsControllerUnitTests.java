package ru.practicum.shareit.itemRequestTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.ItemRequestDto;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemRequestsControllerUnitTests {
    @Autowired
    private ItemRequestController itemRequestController;

    @Autowired
    private UserController userController;

    private ItemRequest itemRequest;

    private User user;


    private ItemRequestDto itemRequestDto;

    private UserDto userDto;

    private UserDto userDto2;

    @BeforeEach
    void init() {
        itemRequest = ItemRequest
                .builder()
                .description("item request description")
                .build();

        itemRequestDto = ItemRequestDto
                .builder()
                .description("item request description")
                .build();

        user = User
                .builder()
                .name("name")
                .email("user@email.com")
                .build();


        userDto = UserDto
                .builder()
                .name("name")
                .email("user@email.com")
                .build();

        userDto2 = UserDto
                .builder()
                .name("name2")
                .email("user2@email.com")
                .build();
    }

    @Test
    void createTest() {
        user.setEmail("u2662y@df.ru2");

        UserDto userDto1 = userController.createUser(user);
        ItemRequestDto itemRequest = itemRequestController.createItemRequest(userDto1.getId(), itemRequestDto);
        assertEquals(3L, itemRequestController.getItemRequest(userDto1.getId(), itemRequest.getId()).getId());
    }

    @Test
    void createByWrongUserTest() {
        assertThrows(NotFoundException.class, () -> itemRequestController.createItemRequest(15425L,
                itemRequestDto));
    }

    @Test
    void createItemRequestTest() {
        UserDto userDto2 = userController.createUser(user);
        ItemRequestDto itemRequest = itemRequestController.createItemRequest(userDto2.getId(), itemRequestDto);
        assertEquals(1, itemRequestController.getItemsRequest(userDto2.getId()).size());
    }

    @Test
    void getItemsRequestTest() {
        assertThrows(NotFoundException.class, () -> itemRequestController.getItemsRequest(1L));
    }

    @Test
    void getAllItemRequestsTest() {
        user.setEmail("u6644y@df.ru");
        UserDto userDTO3 = userController.createUser(user);

        ItemRequestDto itemRequest = itemRequestController.createItemRequest(userDTO3.getId(), itemRequestDto);
        assertEquals(1, itemRequestController.getAllItemRequests(userDTO3.getId(), 0, 10).size());
        user.setEmail("u69gg6424y@df.ru");

        UserDto userDto4 = userController.createUser(user);
        assertEquals(1, itemRequestController.getAllItemRequests(userDto4.getId(), 0, 10).size());
    }

    @Test
    void getAllByWrongUser() {
        assertThrows(NotFoundException.class, () -> itemRequestController.getAllItemRequests(1L, 0, 10));
    }

    @Test
    void getAllWithWrongFrom() {
        assertThrows(BadRequestException.class, () -> itemRequestController
                .getAllItemRequests(1L, 0, -10));
    }
}

