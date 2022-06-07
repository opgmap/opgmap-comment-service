package ru.opgmap.opgmap_comment_service.service;

import ru.opgmap.opgmap_comment_service.model.UserLike;

import java.util.Optional;
import java.util.UUID;

public interface UserLikeService {
    Optional<UserLike> findVote(UUID id, UUID userId);

    void removeVote(UserLike id);

    UserLike saveLike(UserLike userLike);
}
