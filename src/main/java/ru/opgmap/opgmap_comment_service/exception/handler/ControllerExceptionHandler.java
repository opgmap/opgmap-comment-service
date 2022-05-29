package ru.opgmap.opgmap_comment_service.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.opgmap.opgmap_comment_service.exception.model.EntityNotExistsException;
import ru.opgmap.opgmap_comment_service.exception.utils.ErrorMessage;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({EntityNotExistsException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage returnErrorMessage(EntityNotExistsException ex, WebRequest request) {
        return ErrorMessage.builder()
                .path(((ServletWebRequest)request).getRequest().getRequestURI())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
    }
}
