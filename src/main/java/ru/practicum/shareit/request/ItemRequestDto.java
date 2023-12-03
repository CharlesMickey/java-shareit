package ru.practicum.shareit.request;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.user.model.User;

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
