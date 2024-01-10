package ru.practicum.shareit.booking;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Validated
@Builder
public class BookingNextLastDto {

    @Id
    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private Long itemId;

    private Item item;

    private User booker;

    private Long bookerId;

    private BookingStatus status;
}
