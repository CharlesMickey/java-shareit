package ru.practicum.shareit.exceptionTests;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import ru.practicum.shareit.exception.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomExceptionHandlerTest {

    @Test
    void handleValidationException_ReturnsBadRequestWithErrors() throws NoSuchMethodException {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        MethodArgumentNotValidException ex = createValidationException();

        ResponseEntity<Object> responseEntity = customExceptionHandler.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Map<?, ?> responseBody = (Map<?, ?>) responseEntity.getBody();
        assertNotNull(responseBody.get("timestamp"));
        assertNotNull(responseBody.get("fieldName"));
        assertEquals("Field error message", responseBody.get("fieldName"));
    }

    private MethodArgumentNotValidException createValidationException() throws NoSuchMethodException {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "objectName");
        bindingResult.addError(new FieldError(
                "objectName", "fieldName", "Field error message"));
        return new MethodArgumentNotValidException(
                new MethodParameter(CustomExceptionHandler.class.getMethod("handleValidationException", MethodArgumentNotValidException.class), 0),
                bindingResult);
    }

    @Test
    void handleNotFoundException_ReturnsNotFound() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        NotFoundException ex = new NotFoundException("Not found");

        ResponseEntity<Object> responseEntity = customExceptionHandler.handleNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertCommonResponseAttributes(responseEntity.getBody(), "NOT_FOUND", "Not found");
    }

    @Test
    void handleUnsupportedStatusException_ReturnsBadRequest() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        UnsupportedStatusException ex = new UnsupportedStatusException("Unsupported status");

        ResponseEntity<Object> responseEntity = customExceptionHandler.handleUnsupportedStatusException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertCommonResponseAttributes(responseEntity.getBody(), "Unknown state: UNSUPPORTED_STATUS", "Unsupported status");
    }

    @Test
    void handleConflictException_ReturnsConflict() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        ConflictException ex = new ConflictException("Conflict");

        ResponseEntity<Object> responseEntity = customExceptionHandler.handleValidationException(ex);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertCommonResponseAttributes(responseEntity.getBody(), "CONFLICT", "Conflict");
    }

    @Test
    void handleValidationException_ReturnsBadRequest() {
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        BadRequestException ex = new BadRequestException("Bad request");

        ResponseEntity<Object> responseEntity = customExceptionHandler.handleValidationException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertCommonResponseAttributes(responseEntity.getBody(), "BAD_REQUEST", "Bad request");
    }

    private void assertCommonResponseAttributes(Object responseBody, String expectedError, String expectedMessage) {
        assertEquals(LocalDateTime.now().getYear(), ((LocalDateTime) ((Map<?, ?>) responseBody).get("timestamp")).getYear());
        assertEquals(expectedError, ((Map<?, ?>) responseBody).get("error"));
        assertEquals(expectedMessage, ((Map<?, ?>) responseBody).get("message"));
    }

}
