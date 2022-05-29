package ru.opgmap.opgmap_comment_service.mapper;

import org.mapstruct.Mapper;
import ru.opgmap.opgmap_comment_service.dto.CommentDto;
import ru.opgmap.opgmap_comment_service.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    Comment fromDto(CommentDto commentDto);
}
