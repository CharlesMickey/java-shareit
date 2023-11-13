package ru.practicum.shareit.booking;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */

@Data
@Validated
public class Booking {

    private int id;

    @FutureOrPresent(message = "Нельзя бронировать задним числом")
    private LocalDate start;

    @FutureOrPresent(message = "Нельзя бронировать задним числом")
    private LocalDate end;

    private Item item;

    private User booker;

    private BookingStatus status;
}