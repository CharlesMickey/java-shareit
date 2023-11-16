package ru.practicum.shareit.item.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.model.User;

/**
 * TODO Sprint add-controllers.
 */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private int id;

    private String name;

    private String description;

    @NotNull
    @Pattern(
            regexp = "^true$|^false$",
            message = "Available может быть онли: true or false")
    private Boolean available;

    private User owner;

    private ItemRequest request;
}
