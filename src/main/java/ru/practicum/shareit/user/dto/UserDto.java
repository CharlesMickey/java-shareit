package ru.practicum.shareit.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Validated
@Builder
public class UserDto {

    private int id;

    private String name;

    private String email;
}
