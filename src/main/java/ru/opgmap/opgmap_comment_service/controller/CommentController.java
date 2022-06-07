package ru.opgmap.opgmap_comment_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Affordance;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.opgmap.opgmap_comment_service.dto.CommentDto;
import ru.opgmap.opgmap_comment_service.hateoas.assembler.CommentModelAssembler;
import ru.opgmap.opgmap_comment_service.model.Comment;
import ru.opgmap.opgmap_comment_service.service.CommentService;
import ru.opgmap.opgmap_comment_service.utils.PageableUtils;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static ru.opgmap.opgmap_comment_service.hateoas.utils.Relational.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
@SecurityRequirement(name = "security_auth")
@CrossOrigin("*")
@PreAuthorize("isAuthenticated()")
public class CommentController {

    private final CommentService commentService;

    private final CommentModelAssembler commentModelAssembler;
    private final PagedResourcesAssembler<Comment> pagedResourcesAssembler;
    private final Class<CommentController> commentController = CommentController.class;

    @PostMapping
    public EntityModel<CommentDto> saveComment(@Valid @RequestBody CommentDto commentDto) {
        CommentDto response = commentService.saveComment(commentDto);

        return EntityModel.of(response,
                linkTo(methodOn(commentController).saveComment(commentDto)).withSelfRel(),
                linkTo(methodOn(commentController).likeComment(null, response.getId(), true))
                        .withRel(LIKE_COMMENT),
                linkTo(methodOn(commentController).getCommentById(response.getId())).withRel(COMMENT)
                        .andAffordances(singleCommentAffordances(response.getId())),
                getCommentsByDangerZoneLink(response.getDangerZoneId()));
    }

    @GetMapping("/{id}")
    public EntityModel<CommentDto> getCommentById(@PathVariable UUID id) {
        CommentDto response = commentService.getById(id);

        return EntityModel.of(response,
                linkTo(methodOn(commentController).getCommentById(id)).withSelfRel()
                        .andAffordances(singleCommentAffordances(id)),
                getCommentsByDangerZoneLink(response.getDangerZoneId()),
                linkTo(methodOn(commentController).likeComment(null, id, true)).withRel(LIKE_COMMENT));
    }

    @GetMapping
    public PagedModel<?> getCommentsByDangerZone(@RequestParam(value = "danger-zone") UUID dangerZoneId,
                                                 @RequestParam(defaultValue = "0") int cursor,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                 @RequestParam(defaultValue = "0") int asc) {
        Pageable pageable = PageableUtils
                .getPageable(cursor, size, sortBy, asc);

        Page<Comment> responsePage = commentService.getAllCommentsByDangerZoneId(dangerZoneId, pageable);
        return pagedResourcesAssembler.toModel(responsePage, commentModelAssembler);
    }

    @PutMapping("/{id}")
    public EntityModel<CommentDto> updateComment(@PathVariable UUID id, @RequestBody CommentDto commentDto) {
        return EntityModel.of(commentService.updateComment(id, commentDto),
                linkTo(methodOn(commentController).updateComment(id, commentDto)).withSelfRel()
                        .andAffordances(singleCommentAffordances(id)),
                linkTo(methodOn(commentController).likeComment(null, id, true)).withRel(LIKE_COMMENT),
                getCommentsByDangerZoneLink(commentDto.getDangerZoneId()));
    }

    @PutMapping("/{id}/like")
    public EntityModel<CommentDto> likeComment(Principal principal,
                                               @PathVariable UUID id, @RequestParam boolean like) {
        CommentDto commentDto = commentService.likeComment(id, UUID.fromString(principal.getName()), like);

        return EntityModel.of(commentDto,
                linkTo(methodOn(commentController).likeComment(null, id, like)).withSelfRel(),
                linkTo(methodOn(commentController).getCommentById(id)).withRel(COMMENT)
                        .andAffordances(singleCommentAffordances(id)),
                getCommentsByDangerZoneLink(commentDto.getDangerZoneId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable UUID id) {
        commentService.deleteCommentById(id);

        return ResponseEntity.noContent().build();
    }

    private Link getCommentsByDangerZoneLink(UUID dangerZoneId) {
        return linkTo(methodOn(commentController).getCommentsByDangerZone(dangerZoneId,
                0, 10, "id", 0)).withRel(COMMENTS);
    }

    private List<Affordance> singleCommentAffordances(UUID id) {
        return List.of(
                afford(methodOn(commentController).getCommentById(id)),
                afford(methodOn(commentController).updateComment(id, null)),
                afford(methodOn(commentController).deleteComment(id)));
    }

}
