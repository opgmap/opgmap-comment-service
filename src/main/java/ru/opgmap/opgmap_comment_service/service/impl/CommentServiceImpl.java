package ru.opgmap.opgmap_comment_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.opgmap.opgmap_comment_service.dto.CommentDto;
import ru.opgmap.opgmap_comment_service.exception.model.EntityNotExistsException;
import ru.opgmap.opgmap_comment_service.exception.utils.ExceptionMessagesGenerator;
import ru.opgmap.opgmap_comment_service.mapper.CommentMapper;
import ru.opgmap.opgmap_comment_service.model.Comment;
import ru.opgmap.opgmap_comment_service.model.UserLike;
import ru.opgmap.opgmap_comment_service.repository.CommentRepository;
import ru.opgmap.opgmap_comment_service.service.CommentService;
import ru.opgmap.opgmap_comment_service.service.UserLikeService;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService {

    private static final String ENTITY_NAME = "Comment";

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserLikeService userLikeService;

    @Override
    public CommentDto saveComment(CommentDto commentDto) {
        Comment comment = commentMapper.fromDto(commentDto);
        comment.setCreated(LocalDateTime.now());
        comment.setLikes(0L);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public Page<Comment> getAllCommentsByDangerZoneId(UUID dangerZoneId, Pageable pageable) {
        return commentRepository.findAllByDangerZoneId(dangerZoneId, pageable);
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
    public CommentDto likeComment(UUID id, UUID userId, boolean like) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException(
                        ExceptionMessagesGenerator.generateNotFoundMessage(ENTITY_NAME, id)));

        Optional<UserLike> userVoteOptional = userLikeService.findVote(id, userId);

        UserLike userLike;

        if (userVoteOptional.isPresent()) {
            userLike = userVoteOptional.get();
            if (like == userLike.isValue()) {
                userLikeService.removeVote(userLike);
                comment.setLikes(comment.getLikes() + ((like) ? -1 : 1));
            } else {
                userLike.setValue(like);
                userLikeService.saveLike(userLike);
                comment.setLikes(comment.getLikes() + ((like) ? 2 : -2));
            }
        } else {
            userLike = UserLike.builder()
                    .comment(comment)
                    .userId(userId)
                    .value(like)
                    .created(LocalDateTime.now())
                    .build();
            userLikeService.saveLike(userLike);
            comment.setLikes(comment.getLikes() + ((like) ? 1 : -1));
        }

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(UUID id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotExistsException(
                        ExceptionMessagesGenerator.generateNotFoundMessage(ENTITY_NAME, id)));
        commentRepository.delete(comment);
    }

    @Override
    public CommentDto getById(UUID id) {
        return commentMapper.toDto(commentRepository.findById(id).orElseThrow(() -> new EntityNotExistsException(
                ExceptionMessagesGenerator.generateNotFoundMessage(ENTITY_NAME, id)
        )));
    }
}
