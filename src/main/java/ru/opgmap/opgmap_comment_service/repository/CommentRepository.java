package ru.opgmap.opgmap_comment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.opgmap.opgmap_comment_service.model.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

    List<Comment> findAllByDangerZoneId(UUID dangerZoneId);
}
