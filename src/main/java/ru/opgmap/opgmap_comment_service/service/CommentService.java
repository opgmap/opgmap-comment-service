package ru.opgmap.opgmap_comment_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.opgmap.opgmap_comment_service.dto.CommentDto;
import ru.opgmap.opgmap_comment_service.kafka.dto.DeleteDangerZoneDto;
import ru.opgmap.opgmap_comment_service.model.Comment;

import java.util.UUID;

public interface CommentService {

    CommentDto saveComment(CommentDto commentDto);

    Page<Comment> getAllCommentsByDangerZoneId(UUID dangerZoneId, Pageable pageable);

    CommentDto updateComment(UUID id, CommentDto commentDto);

    CommentDto likeComment(UUID id, UUID uuid, boolean like);

    void deleteCommentById(UUID id);

    CommentDto getById(UUID id);

    void deleteCommentsByDangerZone(UUID dangerZoneId);

}
