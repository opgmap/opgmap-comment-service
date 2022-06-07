package ru.opgmap.opgmap_comment_service.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteDangerZoneDto {

    private UUID id;

    private LocalDateTime createdAt;

}
