package ru.practicum.shareit.bookingTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingDto;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingNextLastDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingMapperTests {

    @Autowired
    private BookingMapper bookingMapper;

    private Booking booking;

    @BeforeEach
    void setUp() {
        booking = new Booking(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                new Item(),
                new User(),
                BookingStatus.WAITING);

    }

    @Test
    void toBookingDtoShouldMapCorrectly() {
        BookingDto bookingDto = bookingMapper.toBookingDto(booking);

        assertEquals(1L, bookingDto.getId());
        assertEquals(BookingStatus.WAITING, bookingDto.getStatus());

    }

    @Test
    void toBookingLastNextDtoShouldMapCorrectly() {
        BookingNextLastDto bookingNextLastDto = bookingMapper.toBookingLastNextDto(booking);

        assertEquals(1L, bookingNextLastDto.getId());
        assertEquals(BookingStatus.WAITING, bookingNextLastDto.getStatus());

    }

    @Test
    void toBookingDtoWithNullInputShouldReturnNull() {
        BookingDto bookingDto = bookingMapper.toBookingDto((Booking) null);

        assertNull(bookingDto);
    }

    @Test
    void toListBookingDtoWithNullInputShouldReturnListNull() {
        List<BookingDto> bookingDtoList = bookingMapper.toBookingDto(Collections.emptyList());

        assertTrue(bookingDtoList.isEmpty());
    }

    @Test
    void toBookingLastNextDtoWithNullInputShouldReturnNull() {

        BookingNextLastDto bookingNextLastDto = bookingMapper.toBookingLastNextDto(null);

        assertNull(bookingNextLastDto);
    }

    @Test
    void toBookingWithNullBookingDtoShouldMapOtherFieldsCorrectly() {
        Item item = new Item();
        User user = new User();

        Booking result = bookingMapper.toBooking(null, item, user, BookingStatus.APPROVED);

        assertNull(result.getId());
        assertEquals(item, result.getItem());
        assertEquals(user, result.getBooker());
        assertEquals(BookingStatus.APPROVED, result.getStatus());
    }

    @Test
    void toBookingWithNullInputShouldReturnNull() {
        Booking allNull = bookingMapper.toBooking(
                (BookingDto) null,
                (Item) null,
                (User) null,
                (BookingStatus) null);

        assertNull(allNull);
    }
}

