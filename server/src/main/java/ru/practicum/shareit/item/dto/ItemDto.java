package ru.practicum.shareit.item.dto;

import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.ItemRequest;


@Data
@Builder
public class ItemDto {

    @Id
    private Long id;

    private String name;

    private String description;

    private Boolean available;

    private Long requestId;

    private ItemRequest request;
}
