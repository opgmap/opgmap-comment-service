package ru.opgmap.opgmap_comment_service.exception.model;

public class EntityNotExistsException extends RuntimeException {

    public EntityNotExistsException(String message) {
        super(message);
    }
}
