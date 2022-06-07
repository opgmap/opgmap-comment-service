package ru.opgmap.opgmap_comment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.opgmap.opgmap_comment_service.model.UserLike;

import java.util.Optional;
import java.util.UUID;

@Repository

public interface UserLikeRepository extends JpaRepository<UserLike, UUID> {

    Optional<UserLike> findByUserIdAndComment_Id(UUID userId, UUID comment_id);

}
