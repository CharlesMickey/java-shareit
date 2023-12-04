package ru.practicum.shareit.user.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.validated.Create;
import ru.practicum.shareit.validated.Update;

@Entity
@Table(name = "users", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(groups = {Create.class}, message = "Имя обязательное поле")
    @Column()
    private String name;

    @NotBlank(groups = {Create.class}, message = "Email обязательное поле")
    @Email(groups = {Create.class, Update.class}, message = "Не верный формат email")
    @Column()
    private String email;
}
