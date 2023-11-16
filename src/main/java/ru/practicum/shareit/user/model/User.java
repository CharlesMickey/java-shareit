package ru.practicum.shareit.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    @NotBlank(message = "Имя обязательное поле")
    private String name;

    @NotBlank(message = "Email обязательное поле")
    @Email(message = "Не верный формат email")
    private String email;
}
