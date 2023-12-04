package ru.practicum.shareit.booking;

import java.time.LocalDateTime;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validated.Create;

@Data
@Builder
public class BookingDto {

    private Long id;

    @NotNull(groups = {Create.class}, message = "Start не может быть пустым")
    @FutureOrPresent(groups = {Create.class}, message = "Нельзя бронировать задним числом")
    private LocalDateTime start;

    @NotNull(groups = {Create.class}, message = "End не может быть пустым")
    @FutureOrPresent(groups = {Create.class}, message = "End – время в будущем")
    private LocalDateTime end;


    @NotNull(groups = {Create.class}, message = "ItemId не может быть пустым")
    private Long itemId;

    private Item item;

    private User booker;

    private BookingStatus status;

    @AssertTrue(groups = {Create.class}, message = "Ошибка в датах")
    public boolean isEndDateAfterStartDate() {
        if (end == null || start == null || end.isBefore(start) || end.isEqual(start)) {
            throw new BadRequestException("Ошибка в датах");
        }
        return true;
    }
}
