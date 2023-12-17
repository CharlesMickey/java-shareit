package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.constants.HttpConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpConstantsTest {

    @Test
    void testXSharerUserIdConstant() {
        String expectedConstant = "X-Sharer-User-Id";

        String actualConstant = HttpConstants.X_SHARER_USER_ID;

        assertEquals(expectedConstant, actualConstant);
    }
}
