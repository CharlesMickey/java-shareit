package ru.practicum.shareit.itemsTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.BookingDto;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingsDateDto;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.ItemRequestDto;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ItemControllerUnitTests {
    @Autowired
    private ItemController itemController;

    @Autowired
    private UserController userController;

    @Autowired
    private BookingController bookingController;

    @Autowired
    private ItemRequestController itemRequestController;

    private ItemDto itemDto;

    private ItemDto itemDto2;

    private ItemWithBookingsDateDto itemWithBookingsDateDto;

    private UserDto userDto;
    private User userM;

    private User userM2;

    private ItemRequestDto itemRequestDto;

    private CommentDto comment;

    @BeforeEach
    void init() {
        itemWithBookingsDateDto = ItemWithBookingsDateDto
                .builder()
                .name("name")
                .description("description")
                .available(true)
                .build();

        itemDto = ItemDto
                .builder()
                .name("name")
                .description("description")
                .available(true)
                .build();

        itemDto2 = ItemDto
                .builder()
                .name("new name")
                .description("new description")
                .available(true)
                .build();

        userM = User
                .builder()
                .name("name")
                .email("user@email.com")
                .build();

        userDto = UserDto
                .builder()
                .name("name")
                .email("user@email.com")
                .build();

        userM2 = User
                .builder()
                .name("name")
                .email("user2@email.com")
                .build();

        itemRequestDto = ItemRequestDto
                .builder()
                .description("item request description")
                .build();

        comment = CommentDto
                .builder()
                .text("first comment")
                .build();
    }

    @Test
    void createItemTest() {
        userM.setEmail("w134@er.ru");

        UserDto user = userController.createUser(userM);
        ItemDto item = itemController.createItem(user.getId(), itemDto);
        ItemWithBookingsDateDto ew = itemController.getItemById(8L, 8L);
        assertEquals(item.getId(), ew.getId());
    }

    @Test
    void createItemRequestTest() {
        userM.setEmail("266w1@er.ru");
        UserDto user = userController.createUser(userM);


        ItemRequestDto itemRequest = itemRequestController.createItemRequest(user.getId(), itemRequestDto);
        itemDto.setRequestId(1L);
        UserDto user2 = userController.createUser(userM);
        ItemDto item = itemController.createItem(1L, itemDto);

        ItemWithBookingsDateDto actualItem = itemController.getItemById(1L, 1L);


        assertEquals(item.getName(), actualItem.getName());
        assertEquals(item.getDescription(), actualItem.getDescription());
        assertEquals(item.getAvailable(), actualItem.getAvailable());

    }

    @Test
    void createByWrongUser() throws Throwable {
        assertThrows(NotFoundException.class, () -> itemController.createItem(1411L, itemDto));
    }

    @Test
    void createWithWrongItemRequest() {
        userM.setEmail("2414124@wer.ru");
        itemDto.setRequestId(70L);
        UserDto user = userController.createUser(userM);
        assertThrows(NotFoundException.class, () -> itemController.createItem(11333L, itemDto));
    }

    @Test
    void updateTest() {
        userM.setEmail("w1@er.ru");

        UserDto userDto1 = userController.createUser(userM);
        ItemDto itemDto1 = itemController.createItem(userDto1.getId(), itemDto);
        itemController.updateItem(itemDto1.getId(), userDto1.getId(), itemDto2);
        ItemWithBookingsDateDto item43 = itemController.getItemById(userDto1.getId(), itemDto1.getId());
        assertEquals(itemDto2.getDescription(), item43.getDescription());
    }

    @Test
    void updateForWrongItemTest() {
        assertThrows(NotFoundException.class, () -> itemController.updateItem(1L, 1L, itemDto));
    }

    @Test
    void updateByWrongUserTest() {
        userM.setEmail("1ee@er.ru");

        userController.createUser(userM);
        itemController.createItem(1L, itemDto);
        assertThrows(NotFoundException.class, () -> itemController.updateItem(10323L, 123L, itemDto2));
    }


    @Test
    void searchTest() {
        userM.setEmail("32.@r.ru");
        userController.createUser(userM);
        itemController.createItem(1L, itemDto);
        assertEquals(3, itemController.searchAllItemsByOwnerId("Desc", 0, 10).size());
    }

    @Test
    void searchEmptyTextTest() {
        userM.setEmail("23@w.ru");
        userController.createUser(userM);
        itemController.createItem(1L, itemDto);
        assertEquals(new ArrayList<ItemDto>(), itemController.searchAllItemsByOwnerId("", 0, 10));
    }

    @Test
    void searchWithWrongFrom() {
        assertThrows(BadRequestException.class, () -> itemController
                .searchAllItemsByOwnerId("t", -1, 10));
    }

    @Test
    void createCommentTest() throws InterruptedException {
        userM.setEmail("1@er.ru");
        UserDto user = userController.createUser(userM);
        ItemDto item = itemController.createItem(user.getId(), itemDto);
        UserDto user2 = userController.createUser(userM2);
        bookingController.createBooking(user2.getId(), BookingDto
                .builder()
                .start(LocalDateTime.now().plusSeconds(1))
                .end(LocalDateTime.now().plusSeconds(2))
                .itemId(item.getId()).build(), user2.getId());
        bookingController.approvingBooking(user.getId(), 1L, true);
        TimeUnit.SECONDS.sleep(2);
        itemController.createComment(user2.getId(), item.getId(), comment);
        assertEquals(1, itemController.getItemById(1L, 1L).getComments().size());
    }

    @Test
    void createCommentByWrongUser() {
        assertThrows(NotFoundException.class, () -> itemController.createComment(123L, 1L, comment));
    }

    @Test
    void createCommentToWrongItem() {
        userM.setEmail("144224@er.ru");
        UserDto user = userController.createUser(userM);
        assertThrows(NotFoundException.class, () -> itemController.createComment(1L, 1212L, comment));
        ItemDto item = itemController.createItem(1L, itemDto);
        assertThrows(NotFoundException.class, () -> itemController.createComment(1L, 1212L, comment));
    }

    @Test
    void getAllWithWrongFrom() {
        assertThrows(BadRequestException.class, () -> itemController
                .getAllItemsByOwnerId(1L, -1, 10));
    }
}

