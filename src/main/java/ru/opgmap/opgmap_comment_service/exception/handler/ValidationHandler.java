package ru.opgmap.opgmap_comment_service.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import ru.opgmap.opgmap_comment_service.exception.utils.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<ErrorMessage> returnErrorMessage(MethodArgumentNotValidException ex, WebRequest request) {
        List<ErrorMessage> errorMessages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ErrorMessage errorMessage = ErrorMessage.builder()
                    .path(((ServletWebRequest)request).getRequest().getRequestURI())
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .fieldName(((FieldError) error).getField())
                    .message(error.getDefaultMessage())
                    .build();
            errorMessages.add(errorMessage);
        });
        return errorMessages;
    }
}
