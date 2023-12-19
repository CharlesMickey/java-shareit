package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.constants.HttpConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HttpConstantsTest {

    @Test
    void testXSharerUserIdConstant() {
        final String EXPECTED_X_SHARER_CONSTANT = "X-Sharer-User-Id";

        final String ACTUAL_X_SHARER_CONSTANT = HttpConstants.X_SHARER_USER_ID;

        assertEquals(EXPECTED_X_SHARER_CONSTANT, ACTUAL_X_SHARER_CONSTANT);
    }
}
