package ru.opgmap.opgmap_comment_service.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "danger_zone_id", nullable = false)
    private UUID dangerZoneId;

    @Column(name = "likes", nullable = false)
    private Long likes;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated")
    private LocalDateTime updated;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<UserLike> userLikes;

}
