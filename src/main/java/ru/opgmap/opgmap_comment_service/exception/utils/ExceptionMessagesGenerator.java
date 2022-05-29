package ru.opgmap.opgmap_comment_service.exception.utils;

import java.util.UUID;

public class ExceptionMessagesGenerator {

    private ExceptionMessagesGenerator() {}

    public static String generateNotFoundMessage(String entity, UUID id) {
        return entity + " with id " + id + " not found";
    }
}
