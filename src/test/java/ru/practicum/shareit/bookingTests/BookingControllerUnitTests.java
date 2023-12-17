package ru.practicum.shareit.bookingTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UnsupportedStatusException;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingControllerUnitTests {
    @Autowired
    private BookingController bookingController;

    @Autowired
    private UserController userController;

    @Autowired
    private ItemController itemController;

    @Autowired
    private BookingServiceImpl bookingServiceimpl;

    private ItemDto itemDto;

    private User user;
    private User userM2;
    private UserDto userDto;

    private UserDto userDto1;

    private BookingNextLastDto bookingNextLastDto;
    private BookingDto bookingDto;

    @BeforeEach
    void init() {
        itemDto = ItemDto
                .builder()
                .name("name")
                .description("description")
                .available(true)
                .build();

        user = User
                .builder()
                .name("name")
                .email("user@email.com")
                .build();

        userM2 = User
                .builder()
                .name("name")
                .email("userM2@email.com")
                .build();


        userDto = UserDto
                .builder()
                .name("name")
                .email("user@email.com")
                .build();

        userDto1 = UserDto
                .builder()
                .name("name")
                .email("user1@email.com")
                .build();

        bookingNextLastDto = BookingNextLastDto
                .builder()
                .start(LocalDateTime.of(2023, 12, 24, 12, 30))
                .end(LocalDateTime.of(2023, 12, 25, 13, 0))
                .itemId(1L).build();

        bookingDto = BookingDto
                .builder()
                .start(LocalDateTime.of(2023, 12, 24, 12, 30))
                .end(LocalDateTime.of(2023, 12, 25, 13, 0))
                .status(BookingStatus.APPROVED)
                .itemId(1L).build();
    }

    @Test
    void testMapToStateString() {

        assertEquals("CURRENT", bookingServiceimpl.mapToStateString("CURRENT"));
        assertEquals("PAST", bookingServiceimpl.mapToStateString("PAST"));
        assertEquals("FUTURE", bookingServiceimpl.mapToStateString("FUTURE"));
        assertEquals("WAITING", bookingServiceimpl.mapToStateString("WAITING"));
        assertEquals("REJECTED", bookingServiceimpl.mapToStateString("REJECTED"));
        assertEquals("ALL", bookingServiceimpl.mapToStateString("ALL"));

        Exception exception = assertThrows(UnsupportedStatusException.class, () ->
                bookingServiceimpl.mapToStateString("UNKNOWN_STATE"));
        assertEquals("Unknown state: UNKNOWN_STATE", exception.getMessage());
    }

    @Test
    void createUserTest() {
        UserDto user1 = userController.createUser(user);
        ItemDto item = itemController.createItem(user1.getId(), itemDto);
        UserDto user2 = userController.createUser(userM2);
        BookingDto booking = bookingController.createBooking(user2.getId(), BookingDto
                .builder()
                .start(LocalDateTime.of(2023, 12, 24, 12, 30))
                .end(LocalDateTime.of(2023, 12, 25, 13, 0))
                .status(BookingStatus.APPROVED)
                .itemId(item.getId()).build());
        assertEquals(booking.getId(), bookingController.getBookingById(user1.getId(), booking.getId()).getId());
    }

    @Test
    void createWrongUserTest() {
        assertThrows(NotFoundException.class, () -> bookingController.createBooking(1423L, bookingDto));
    }


    @Test
    void createByOwnerTest() {
        UserDto userqwe = userController.createUser(user);
        ItemDto item = itemController.createItem(userqwe.getId(), itemDto);
        assertThrows(NotFoundException.class, () -> bookingController
                .createBooking(1L, bookingDto));
    }

    @Test
    void createToUnavailableTest() {
        UserDto user12 = userController.createUser(user);
        itemDto.setAvailable(false);
        ItemDto item = itemController.createItem(user12.getId(), itemDto);
        UserDto user134 = userController.createUser(userM2);
        assertThrows(NotFoundException.class, () -> bookingController
                .createBooking(user134.getId(), bookingDto));
    }

    @Test
    void createWithWrongEndDate() {
        UserDto usere = userController.createUser(user);
        ItemDto item = itemController.createItem(usere.getId(), itemDto);
        UserDto user13 = userController.createUser(userM2);
        bookingNextLastDto.setEnd(LocalDateTime.of(2022, 9, 24, 12, 30));
        assertThrows(NotFoundException.class, () -> bookingController
                .createBooking(user13.getId(), bookingDto));
    }

    @Test
    void approvingBookingTest() {
        UserDto usewer = userController.createUser(user);
        ItemDto item = itemController.createItem(usewer.getId(), itemDto);
        UserDto user13 = userController.createUser(userM2);
        BookingDto booking = bookingController.createBooking(user13.getId(), BookingDto
                .builder()
                .start(LocalDateTime.of(2023, 12, 24, 12, 30))
                .end(LocalDateTime.of(2023, 12, 25, 13, 0))
                .status(BookingStatus.APPROVED)
                .itemId(item.getId()).build());
        assertEquals(BookingStatus.WAITING, bookingController.getBookingById(user13.getId(), booking.getId()).getStatus());
        bookingController.approvingBooking(user.getId(), booking.getId(), true);
        assertEquals(BookingStatus.APPROVED, bookingController
                .getBookingById(user13.getId(), booking.getId()).getStatus());
    }

    @Test
    void approvingBookingToWrongBookingTest() {
        assertThrows(NotFoundException.class,
                () -> bookingController.approvingBooking(1L, 1L, true));
    }

    @Test
    void approvingBookingByWrongUserTest() {
        UserDto usewer = userController.createUser(user);
        ItemDto item = itemController.createItem(usewer.getId(), itemDto);
        UserDto user132 = userController.createUser(userM2);
        BookingDto booking = bookingController.createBooking(user132.getId(), BookingDto
                .builder()
                .start(LocalDateTime.of(2023, 12, 24, 12, 30))
                .end(LocalDateTime.of(2023, 12, 25, 13, 0))
                .status(BookingStatus.APPROVED)
                .itemId(item.getId()).build());
        assertThrows(NotFoundException.class,
                () -> bookingController.approvingBooking(1L, 2L, true));
    }

    @Test
    void approveToBookingWithWrongStatus() {
        UserDto user3 = userController.createUser(user);
        ItemDto item = itemController.createItem(user3.getId(), itemDto);
        UserDto user1 = userController.createUser(userM2);
        BookingDto booking = bookingController.createBooking(user1.getId(), BookingDto
                .builder()
                .start(LocalDateTime.of(2023, 12, 24, 12, 30))
                .end(LocalDateTime.of(2023, 12, 25, 13, 0))
                .status(BookingStatus.APPROVED)
                .itemId(item.getId()).build());
        bookingController.approvingBooking(user3.getId(), booking.getId(), true);
        assertThrows(BadRequestException.class,
                () -> bookingController.approvingBooking(user3.getId(), booking.getId(), true));
    }

    @Test
    void getAllByUserTest() {
        UserDto usewerr = userController.createUser(user);
        ItemDto item = itemController.createItem(usewerr.getId(), itemDto);
        UserDto user1 = userController.createUser(userM2);
        BookingDto booking = bookingController.createBooking(user1.getId(), BookingDto
                .builder()
                .start(LocalDateTime.of(2023, 12, 24, 12, 30))
                .end(LocalDateTime.of(2023, 12, 25, 13, 0))
                .status(BookingStatus.APPROVED)
                .itemId(item.getId()).build());
        assertEquals(1,
                bookingController.findUserBookingsWithState(user1.getId(), "WAITING", 0, 10).size());
        bookingController.approvingBooking(user.getId(), booking.getId(), true);
        assertEquals(1, bookingController.findOwnerBookingsWithState(
                user.getId(), "ALL", 0, 10).size());

    }

    @Test
    void getByWrongPaginationTest() {
        assertThrows(BadRequestException.class, () -> bookingController
                .findUserBookingsWithState(1L, "ALL", 0, -10));

        assertThrows(BadRequestException.class, () -> bookingController
                .findOwnerBookingsWithState(1L, "ALL", 0, -10));


    }

    @Test
    void getByWrongItemNotAvailableTest() {
        UserDto usewerr = userController.createUser(user);
        ItemDto item = itemController.createItem(usewerr.getId(), ItemDto
                .builder()
                .name("name")
                .description("description")
                .available(false)
                .build());
        UserDto user1 = userController.createUser(userM2);

        assertThrows(NotFoundException.class, () -> bookingController
                .createBooking(user1.getId(), bookingDto));


    }

    @Test
    void getAllByWrongUserTest() {
        assertThrows(NotFoundException.class, () -> bookingController
                .findUserBookingsWithState(1L, "ALL", 0, 10));
        assertThrows(NotFoundException.class, () -> bookingController
                .findOwnerBookingsWithState(1L, "ALL", 0, 10));
    }

    @Test
    void getByWrongIdTest() {
        assertThrows(NotFoundException.class, () -> bookingController
                .getBookingById(1L, 1L));
    }
}
