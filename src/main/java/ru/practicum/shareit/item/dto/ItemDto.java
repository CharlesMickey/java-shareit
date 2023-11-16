package ru.practicum.shareit.item.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Validated
@Builder
public class ItemDto {

    private int id;

    @NotNull(message = "Name не может быть пустым")
    @NotEmpty(message = "Name не может быть пустым")
    private String name;

    @NotNull(message = "Name не может быть пустым")
    @NotEmpty(message = "Name не может быть пустым")
    private String description;

    @NotNull(message = "Available не может быть пустым")
    private Boolean available;
}
