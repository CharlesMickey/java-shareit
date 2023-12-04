package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.constants.HttpConstants;
import ru.practicum.shareit.validated.Create;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public BookingDto createBooking(@RequestHeader(HttpConstants.X_SHARER_USER_ID) Long bookerId,
                                    @Validated(Create.class) @RequestBody BookingDto bookingDto) {
        log.info("Post request /users, data transmitted: {}", bookingDto);

        return bookingService.createBooking(bookerId, bookingDto);

    }

    @PatchMapping("/{bookingId}")
    public BookingDto approvingBooking(@RequestHeader(HttpConstants.X_SHARER_USER_ID) Long ownerId,
                                       @PathVariable Long bookingId,
                                       @RequestParam(value = "approved") Boolean approved) {
        log.info("Patch request /booking approved, data transmitted: {}", approved);

        return bookingService.approvingBooking(bookingId, ownerId, approved);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getBookingById(@RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
                                     @PathVariable Long bookingId) {
        log.info("Get request /booking, userId: {},  and bookingId: {} ", userId, bookingId);

        return bookingService.getBookingById(bookingId, userId);
    }

    @GetMapping()
    public List<BookingDto> findUserBookingsWithState(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @RequestParam(value = "state", required = false, defaultValue = "ALL")
            String status) {
        log.info("Get with params state, userId: {}, status: {}", userId, status);
        return bookingService.findUserBookingsWithState(userId, status);
    }

    @GetMapping("/owner")
    public List<BookingDto> findOwnerBookingsWithState(@RequestHeader(HttpConstants.X_SHARER_USER_ID) Long ownerId,
                                                       @RequestParam(value = "state", defaultValue = "ALL")
                                                       String status) {
        log.info("Get with params state, userId: {}, status: {}", ownerId, status);
        return bookingService.findOwnerBookingsWithState(ownerId, status);
    }
}
