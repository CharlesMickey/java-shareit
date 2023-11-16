package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
public class ItemDtoUpdate {

    private Integer id;

    private String name;

    private String description;

    private Boolean available;
}
