package ru.practicum.shareit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "db.name=test")
class ShareItTests {

    @Test
    void contextLoads() {
    }

}
