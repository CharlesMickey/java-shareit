package ru.practicum.shareit.items.commetDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.validated.Create;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    @NotBlank(groups = {Create.class}, message = "Комментарий не может быть пустым")
    private String text;
}
