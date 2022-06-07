package ru.opgmap.opgmap_comment_service.hateoas.assembler;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import ru.opgmap.opgmap_comment_service.controller.CommentController;
import ru.opgmap.opgmap_comment_service.dto.CommentDto;
import ru.opgmap.opgmap_comment_service.mapper.CommentMapper;
import ru.opgmap.opgmap_comment_service.model.Comment;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static ru.opgmap.opgmap_comment_service.hateoas.utils.Relational.COMMENT;
import static ru.opgmap.opgmap_comment_service.hateoas.utils.Relational.LIKE_COMMENT;

@Component
@RequiredArgsConstructor
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, EntityModel<CommentDto>> {

    private final CommentMapper commentMapper;

    private final Class<CommentController> commentController = CommentController.class;

    @Override
    public EntityModel<CommentDto> toModel(Comment comment) {

        CommentDto commentDto = commentMapper.toDto(comment);
        UUID id = commentDto.getId();

        return EntityModel.of(commentDto,
                linkTo(methodOn(commentController).getCommentById(id)).withRel(COMMENT)
                        .andAffordances(List.of(
                                afford(methodOn(commentController).updateComment(id, null)),
                                afford(methodOn(commentController).deleteComment(id)))),
                linkTo(methodOn(commentController).likeComment(null, id, true)).withRel(LIKE_COMMENT));
    }
}
