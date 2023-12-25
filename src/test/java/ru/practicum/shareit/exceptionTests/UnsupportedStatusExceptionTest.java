package ru.practicum.shareit.exceptionTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.UnsupportedStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UnsupportedStatusExceptionTest {

    @Test
    void testConstructorAndGetMessage() {
        String message = "Test message";
        UnsupportedStatusException customException = new UnsupportedStatusException(message);
        assertEquals(message, customException.getMessage());
    }


}

