package ru.opgmap.opgmap_comment_service.kafka.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.opgmap.opgmap_comment_service.kafka.dto.DeleteDangerZoneDto;
import ru.opgmap.opgmap_comment_service.service.CommentService;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class KafkaListeners {

    private final CommentService commentService;

    @Bean
    public Consumer<DeleteDangerZoneDto> deleteDangerZone() {
        return (dto) -> commentService.deleteCommentsByDangerZone(dto.getId());
    }

}
