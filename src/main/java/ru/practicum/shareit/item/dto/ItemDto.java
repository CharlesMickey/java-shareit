package ru.practicum.shareit.item.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.validated.Create;


@Data
@Builder
public class ItemDto {

    private Long id;

    @NotBlank(groups = {Create.class}, message = "Name не может быть пустым")
    private String name;

    @NotBlank(groups = {Create.class}, message = "Description не может быть пустым")
    private String description;

    @NotNull(groups = {Create.class}, message = "Available не может быть пустым")
    private Boolean available;
}
