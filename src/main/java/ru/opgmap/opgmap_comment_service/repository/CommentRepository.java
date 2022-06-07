package ru.opgmap.opgmap_comment_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.opgmap.opgmap_comment_service.model.Comment;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID>,
        PagingAndSortingRepository<Comment, UUID> {

    Page<Comment> findAllByDangerZoneId(UUID dangerZoneId, Pageable pageable);


}
