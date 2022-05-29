package ru.opgmap.opgmap_comment_service.exception.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String path;
    private int statusCode;
    private String fieldName;
    private String message;
}
