package ru.practicum.shareit.request;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.model.User;

@Data
@Builder
public class ItemRequestDto {

    private Long id;

    private String description;

    private User requestor;

    private LocalDateTime created;

    private List<ItemDto> items;
}
