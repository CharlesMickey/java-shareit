package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.UnsupportedStatusException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;

    public BookingDto createBooking(Long bookerId, BookingDto bookingDto) {
        User booker = userRepository.findById(bookerId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemRepository.findByIdAndOwnerIdNot(bookingDto.getItemId(), bookerId)
                .orElseThrow(() -> new NotFoundException("Вещь доступная для бронирования не найдена"));

        if (item.getAvailable() == false) {
            throw new BadRequestException("Вещь недоступна");
        }

        return BookingMapper.toBookingDto(bookingRepository
                .save(BookingMapper.toBooking(bookingDto, item, booker, BookingStatus.WAITING)));
    }

    public BookingDto approvingBooking(Long bookingId, Long ownerId, Boolean approved) {

        Booking booking = bookingRepository.findBookingByBookingIdAndOwnerId(bookingId, ownerId)
                .orElseThrow(() -> new NotFoundException("Бронирование не найдено"));

        BookingStatus status = booking.getStatus();
        if (BookingStatus.APPROVED.equals(status) || BookingStatus.REJECTED.equals(status)) {
            throw new BadRequestException("Вы уже меняли статус");
        }

        booking.setStatus(approved == true ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return BookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    public BookingDto getBookingById(Long bookingId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Booking booking = bookingRepository.findBookingByBookingIdAndOwnerIdOrOwnerItemId(bookingId, userId)
                .orElseThrow(() -> new NotFoundException("Ничего не найдено"));

        return BookingMapper.toBookingDto(booking);
    }


    public List<BookingDto> findUserBookingsWithState(Long userId, String status) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return BookingMapper.toBookingDto(bookingRepository
                .findUserBookingsWithState(userId, mapToStateString(status)));
    }

    public List<BookingDto> findOwnerBookingsWithState(Long ownerId, String status) {
        userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return BookingMapper.toBookingDto(bookingRepository
                .findOwnerBookingsWithState(ownerId, mapToStateString(status)));
    }

    private String mapToStateString(String state) {
        switch (state.toUpperCase()) {
            case "CURRENT":
                return "CURRENT";
            case "PAST":
                return "PAST";
            case "FUTURE":
                return "FUTURE";
            case "WAITING":
                return "WAITING";
            case "REJECTED":
                return "REJECTED";
            case "ALL":
                return "ALL";
            default:
                throw new UnsupportedStatusException("Unknown state: " + state);
        }
    }
}
