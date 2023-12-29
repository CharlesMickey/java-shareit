package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.booking.BookingNextLastDto;
import ru.practicum.shareit.item.comment.CommentDto;

import javax.persistence.Id;
import java.util.List;

@Data
@Validated
@Builder
public class ItemWithBookingsDateDto {

    @Id
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private BookingNextLastDto lastBooking;

    private BookingNextLastDto nextBooking;

    private List<CommentDto> comments;
}
