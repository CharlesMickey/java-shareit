package ru.practicum.shareit.item.comment;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getAuthor(),
                comment.getAuthor().getName(),
                comment.getItem(),
                comment.getCreated()
        );
    }

    public List<CommentDto> toCommentDto(List<Comment> listComments) {
        List<CommentDto> listCommentsDto = new ArrayList<>();

        for (Comment comment : listComments) {
            listCommentsDto.add(toCommentDto(comment));
        }

        return listCommentsDto;
    }


    public Comment toComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getAuthor(),
                commentDto.getItem(),
                null);
    }
}
