package ru.practicum.shareit.booking;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validated.Create;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Validated
@Builder
public class BookingNextLastDto {
    private Long id;

    @NotNull(groups = {Create.class}, message = "Start не может быть пустым")
    @FutureOrPresent(groups = {Create.class}, message = "Нельзя бронировать задним числом")
    private LocalDateTime start;

    @NotNull(groups = {Create.class}, message = "End не может быть пустым")
    @FutureOrPresent(groups = {Create.class}, message = "Нельзя бронировать задним числом")
    private LocalDateTime end;

    @NotNull(groups = {Create.class}, message = "ItemId не может быть пустым")
    private Long itemId;

    private Item item;

    private User booker;

    private Long bookerId;

    private BookingStatus status;
}
