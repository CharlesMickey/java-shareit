package ru.practicum.shareit.user.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Validated
@Builder
public class User {

    private int id;

    @NotBlank(message = "Имя обязательное поле")
    private String name;

    @NotBlank(message = "Email обязательное поле")
    @Email(message = "Не верный формат email")
    private String email;
}
