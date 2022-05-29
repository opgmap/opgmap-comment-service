package ru.opgmap.opgmap_comment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private UUID id;

    @NotBlank
    private String message;

    @NotNull
    private UUID userId;

    @NotNull
    private UUID dangerZoneId;

    private Long likes;

    private LocalDateTime created;

    private LocalDateTime updated;
}
