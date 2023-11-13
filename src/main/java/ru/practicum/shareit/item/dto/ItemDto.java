package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Validated
@Builder
public class ItemDto {

    private int id;

    private String name;

    private String description;

    private boolean available;

    private Integer owner;

    private Integer request;
}