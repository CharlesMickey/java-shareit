package ru.practicum.shareit.booking.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

import lombok.*;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.validated.Create;

@Data
@Builder
public class BookItemRequestDto {

    @NotNull(groups = {Create.class}, message = "ItemId не может быть пустым")
    private Long itemId;

    @NotNull(groups = {Create.class}, message = "Start не может быть пустым")
    @FutureOrPresent(groups = {Create.class}, message = "Нельзя бронировать задним числом")
    private LocalDateTime start;

    @NotNull(groups = {Create.class}, message = "End не может быть пустым")
    @Future(groups = {Create.class}, message = "End – время в будущем")
    private LocalDateTime end;

    @AssertTrue(groups = {Create.class}, message = "Ошибка в датах")
    public boolean isEndDateAfterStartDate() {
        if (end == null || start == null || end.isBefore(start) || end.isEqual(start)) {
            throw new BadRequestException("Ошибка в датах");
        }
        return true;
    }
}
