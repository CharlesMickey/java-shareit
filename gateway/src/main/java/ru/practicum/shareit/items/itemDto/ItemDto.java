package ru.practicum.shareit.items.itemDto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.validated.Create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ItemDto {

    @NotBlank(groups = {Create.class}, message = "Name не может быть пустым")
    private String name;

    @NotBlank(groups = {Create.class}, message = "Description не может быть пустым")
    private String description;

    @NotNull(groups = {Create.class}, message = "Available не может быть пустым")
    private Boolean available;

    private Long requestId;
}
