package ru.practicum.shareit.booking;

import java.util.List;

public interface BookingService {

    BookingDto createBooking(Long id, BookingDto bookingDto);

    BookingDto approvingBooking(Long ownerId, Long bookingId, Boolean approved);

    BookingDto getBookingById(Long bookingId, Long userId);

    List<BookingDto> findUserBookingsWithState(Long userId, String status);

    List<BookingDto> findOwnerBookingsWithState(Long ownerId, String status);

}
