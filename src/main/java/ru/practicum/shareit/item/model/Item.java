package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;


@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private int id;

    private String name;

    private String description;

    private Boolean available;

    private User owner;

    private ItemRequest request;
}
