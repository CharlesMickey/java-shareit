package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.validated.Create;
import ru.practicum.shareit.validated.Update;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;

    @NotBlank(groups = {Create.class}, message = "Имя обязательное поле")
    private String name;

    @NotBlank(groups = {Create.class}, message = "Email обязательное поле")
    @Email(groups = {Create.class, Update.class}, message = "Не верный формат email")
    private String email;
}
