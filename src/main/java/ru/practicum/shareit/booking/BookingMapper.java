package ru.practicum.shareit.booking;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BookingMapper {
    public BookingDto toBookingDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getItem(),
                booking.getBooker(),
                booking.getStatus()
        );
    }

    public BookingNextLastDto toBookingLastNextDto(Booking booking) {
        if(booking == null || booking.getStatus() == BookingStatus.REJECTED) return  null;

        return new BookingNextLastDto(
                booking.getId(),
                booking.getStart(),
                booking.getEnd(),
                booking.getItem().getId(),
                booking.getItem(),
                booking.getBooker(),
               booking.getBooker().getId(),
                booking.getStatus()
        );
    }


    public List<BookingDto> toBookingDto(List<Booking> listBookings) {
        List<BookingDto> listDto = new ArrayList<>();

        for (Booking booking : listBookings) {
            listDto.add(toBookingDto(booking));
        }

        return listDto;
    }

    public Booking toBooking(BookingDto bookingDto, Item item, User booker, BookingStatus status) {
        return new Booking(
                bookingDto.getId(),
                bookingDto.getStart(),
                bookingDto.getEnd(),
                item,
                booker,
                status
        );
    }
}
