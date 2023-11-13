package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@Validated
@Builder
public class ItemRequestDto {

    private int id;

    private String description;

    private User requestor;

    private LocalDate created;
}
