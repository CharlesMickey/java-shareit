package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.practicum.shareit.exception.*;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomExceptionHandlerTest {

    @Test
    void handleNotFoundException_ReturnsNotFound() {
        // Arrange
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        NotFoundException ex = new NotFoundException("Not found");

        // Act
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleNotFoundException(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertCommonResponseAttributes(responseEntity.getBody(), "NOT_FOUND", "Not found");
    }

    @Test
    void handleUnsupportedStatusException_ReturnsBadRequest() {
        // Arrange
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        UnsupportedStatusException ex = new UnsupportedStatusException("Unsupported status");

        // Act
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleUnsupportedStatusException(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertCommonResponseAttributes(responseEntity.getBody(), "Unknown state: UNSUPPORTED_STATUS", "Unsupported status");
    }

    @Test
    void handleConflictException_ReturnsConflict() {
        // Arrange
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        ConflictException ex = new ConflictException("Conflict");

        // Act
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleValidationException(ex);

        // Assert
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertCommonResponseAttributes(responseEntity.getBody(), "CONFLICT", "Conflict");
    }

    @Test
    void handleValidationException_ReturnsBadRequest() {
        // Arrange
        CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();
        BadRequestException ex = new BadRequestException("Bad request");

        // Act
        ResponseEntity<Object> responseEntity = customExceptionHandler.handleValidationException(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertCommonResponseAttributes(responseEntity.getBody(), "BAD_REQUEST", "Bad request");
    }

    private void assertCommonResponseAttributes(Object responseBody, String expectedError, String expectedMessage) {
        assertEquals(LocalDateTime.now().getYear(), ((LocalDateTime) ((Map<?, ?>) responseBody).get("timestamp")).getYear());
        assertEquals(expectedError, ((Map<?, ?>) responseBody).get("error"));
        assertEquals(expectedMessage, ((Map<?, ?>) responseBody).get("message"));
    }

}
