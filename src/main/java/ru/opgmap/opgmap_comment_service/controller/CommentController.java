package ru.opgmap.opgmap_comment_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.opgmap.opgmap_comment_service.dto.CommentDto;
import ru.opgmap.opgmap_comment_service.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment/")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public UUID saveComment(@Valid @RequestBody CommentDto commentDto) {
        return commentService.saveComment(commentDto);
    }

    @GetMapping("/{dangerZoneId}")
    public List<CommentDto> getCommentById(@PathVariable UUID dangerZoneId) {
        return commentService.getAllCommentsByDangerZoneId(dangerZoneId);
    }

    @PutMapping("/{id}")
    public CommentDto updateComment(@PathVariable UUID id, @RequestBody CommentDto commentDto) {
        return commentService.updateComment(id, commentDto);
    }

    @PutMapping("/{id}/like")
    public UUID likeComment(@PathVariable UUID id, @RequestParam boolean like) {
        return commentService.likeComment(id, like);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable UUID id) {
        commentService.deleteCommentById(id);
    }
}
