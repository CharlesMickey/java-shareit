package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookItemRequestDto;
import ru.practicum.shareit.constants.HttpConstants;
import ru.practicum.shareit.validated.Create;


@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<Object> createBooking(@RequestHeader(HttpConstants.X_SHARER_USER_ID) Long bookerId,
                                    @Validated(Create.class) @RequestBody BookItemRequestDto bookingDto) {
        log.info("Post request /users, data transmitted: {}", bookingDto);

        return bookingClient.createBooking(bookerId, bookingDto);

    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approvingBooking(@RequestHeader(HttpConstants.X_SHARER_USER_ID) Long ownerId,
                                       @PathVariable Long bookingId,
                                       @RequestParam(value = "approved") Boolean approved) {
        log.info("Patch request /booking approved, data transmitted: {}", approved);

        return bookingClient.approvingBooking(bookingId, ownerId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBookingId(@RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
                                     @PathVariable Long bookingId) {
        log.info("Get request /booking, userId: {},  and bookingId: {} ", userId, bookingId);

        return bookingClient.getBookingId(bookingId, userId);
    }

    @GetMapping()
    public ResponseEntity<Object> findUserBookingsWithState(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long userId,
            @RequestParam(value = "state", defaultValue = "ALL") String status,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Get with params state, userId: {}, status: {},  from: {}, size: {}", userId, status, from, size);
        return bookingClient.findUserBookingsWithState(userId, status, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> findOwnerBookingsWithState(
            @RequestHeader(HttpConstants.X_SHARER_USER_ID) Long ownerId,
            @RequestParam(value = "state", defaultValue = "ALL") String status,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Get with params state, userId: {}, status: {},  from: {}, size: {}", ownerId, status, from, size);
        return bookingClient.findOwnerBookingsWithState(ownerId, status, from, size);
    }
}