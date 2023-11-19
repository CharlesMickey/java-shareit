package ru.practicum.shareit.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.validated.Create;
import ru.practicum.shareit.validated.Update;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private int id;

    @NotBlank(groups = {Create.class}, message = "Имя обязательное поле")
    private String name;

    @NotBlank(groups = {Create.class}, message = "Email обязательное поле")
    @Email(groups = {Create.class, Update.class}, message = "Не верный формат email")
    private String email;
}
