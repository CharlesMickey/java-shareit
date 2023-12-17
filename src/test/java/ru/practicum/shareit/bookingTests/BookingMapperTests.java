package ru.practicum.shareit.bookingTests;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class BookingMapperTests {

    @Autowired
    private BookingMapper bookingMapper;

    @Test
    void toBookingDtoShouldMapCorrectly() {
        Booking booking = new Booking(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                new Item(),
                new User(),
                BookingStatus.WAITING);


        BookingDto bookingDto = bookingMapper.toBookingDto(booking);


        assertEquals(1L, bookingDto.getId());
        assertEquals(BookingStatus.WAITING, bookingDto.getStatus());

    }

    @Test
    void toBookingLastNextDtoShouldMapCorrectly() {
        Booking booking = new Booking(
                1L,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1),
                new Item(),
                new User(),
                BookingStatus.WAITING);


        BookingNextLastDto bookingNextLastDto = bookingMapper.toBookingLastNextDto(booking);


        assertEquals(1L, bookingNextLastDto.getId());
        assertEquals(BookingStatus.WAITING, bookingNextLastDto.getStatus());

    }

    @Test
    void toBookingDtoWithNullInputShouldReturnNull() {

        BookingDto bookingDto = bookingMapper.toBookingDto((Booking) null);

        assertNull(bookingDto);
    }

}

