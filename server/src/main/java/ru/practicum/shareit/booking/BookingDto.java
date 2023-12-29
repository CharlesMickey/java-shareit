package ru.practicum.shareit.booking;

import java.time.LocalDateTime;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;

@Data
@Builder
public class BookingDto {

    @Id
    private Long id;

    private LocalDateTime start;

    private LocalDateTime end;

    private Long itemId;

    private Item item;

    private User booker;

    private BookingStatus status;

}
