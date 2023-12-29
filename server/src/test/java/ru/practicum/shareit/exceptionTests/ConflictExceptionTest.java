package ru.practicum.shareit.exceptionTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.ConflictException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class ConflictExceptionTest {

    @Test
    void testConstructorAndGetMessage() {
        String message = "Test message";
        ConflictException customException = new ConflictException(message);
        assertEquals(message, customException.getMessage());
    }
}