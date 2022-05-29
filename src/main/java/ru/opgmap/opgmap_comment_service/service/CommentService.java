package ru.opgmap.opgmap_comment_service.service;

import ru.opgmap.opgmap_comment_service.dto.CommentDto;

import java.util.List;
import java.util.UUID;

public interface CommentService {

    UUID saveComment(CommentDto commentDto);

    List<CommentDto> getAllCommentsByDangerZoneId(UUID dangerZoneId);

    CommentDto updateComment(UUID id, CommentDto commentDto);

    UUID likeComment(UUID id, boolean like);

    void deleteCommentById(UUID id);
}
