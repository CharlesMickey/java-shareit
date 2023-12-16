package ru.practicum.shareit.request;

import javax.validation.constraints.NotBlank;

public class ItemRequestCreateDto {
    @NotBlank(message = "Заполните описание запроса")
    private String description;

}
