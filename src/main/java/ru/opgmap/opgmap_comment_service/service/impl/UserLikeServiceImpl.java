package ru.opgmap.opgmap_comment_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.opgmap.opgmap_comment_service.model.UserLike;
import ru.opgmap.opgmap_comment_service.repository.UserLikeRepository;
import ru.opgmap.opgmap_comment_service.service.UserLikeService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserLikeServiceImpl implements UserLikeService {

    private final UserLikeRepository likeRepository;

    @Override
    public Optional<UserLike> findVote(UUID commentId, UUID userId) {
        return likeRepository.findByUserIdAndComment_Id(userId, commentId);
    }

    @Override
    public void removeVote(UserLike userLike) {
        likeRepository.delete(userLike);
    }

    @Override
    public UserLike saveLike(UserLike userLike) {
        return likeRepository.save(userLike);
    }

}
