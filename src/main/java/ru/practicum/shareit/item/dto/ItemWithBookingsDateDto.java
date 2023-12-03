package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.BookingNextLastDto;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.validated.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Validated
@Builder
public class ItemWithBookingsDateDto {

    private Long id;

    @NotBlank(groups = {Create.class}, message = "Name не может быть пустым")
    private String name;

    @NotBlank(groups = {Create.class}, message = "Description не может быть пустым")
    private String description;

    @NotNull(groups = {Create.class}, message = "Available не может быть пустым")
    private Boolean available;

    private BookingNextLastDto lastBooking;

    private BookingNextLastDto nextBooking;

    private List<CommentDto> comments;

}
