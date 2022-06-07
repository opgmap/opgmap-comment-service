package ru.opgmap.opgmap_comment_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.opgmap.opgmap_comment_service.dto.CommentDto;
import ru.opgmap.opgmap_comment_service.exception.model.EntityNotExistsException;
import ru.opgmap.opgmap_comment_service.exception.utils.ExceptionMessagesGenerator;
import ru.opgmap.opgmap_comment_service.mapper.CommentMapper;
import ru.opgmap.opgmap_comment_service.model.Comment;
import ru.opgmap.opgmap_comment_service.repository.CommentRepository;
import ru.opgmap.opgmap_comment_service.service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private static final String ENTITY_NAME = "Comment";

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public UUID saveComment(CommentDto commentDto) {
        Comment comment = commentMapper.fromDto(commentDto);
        comment.setCreated(LocalDateTime.now());
        comment.setLikes(0L);
        return commentRepository.save(comment).getId();
    }

    @Override
    public List<CommentDto> getAllCommentsByDangerZoneId(UUID dangerZoneId) {
        List<Comment> comments = commentRepository.findAllByDangerZoneId(dangerZoneId);
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(UUID id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException(
                        ExceptionMessagesGenerator.generateNotFoundMessage(ENTITY_NAME, id)));
        commentDto.setId(id);
        commentDto.setUpdated(LocalDateTime.now());
        return commentMapper.toDto(commentRepository.save(commentMapper.fromDto(commentDto)));
    }

    @Override
    public UUID likeComment(UUID id, boolean like) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException(
                        ExceptionMessagesGenerator.generateNotFoundMessage(ENTITY_NAME, id)));
        comment.setLikes(comment.getLikes() + (like ? 1 : -1));
        commentRepository.save(comment);
        return comment.getId();
    }

    @Override
    public void deleteCommentById(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException(
                        ExceptionMessagesGenerator.generateNotFoundMessage(ENTITY_NAME, id)));
        commentRepository.delete(comment);
    }
}
