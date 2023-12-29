package ru.practicum.shareit.usersTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.user.dto.UserDto;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
public class UserDtoJsonTests {

    @Autowired
    JacksonTester<UserDto> json;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(10L);
        userDto.setName("user10");
        userDto.setEmail("us1e0r@mail.ru");
    }

    @Test
    void testUserDtoId() throws Exception {
        JsonContent<UserDto> result = json.write(userDto);
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(10);
    }

    @Test
    void testUserDtoName() throws Exception {
        JsonContent<UserDto> result = json.write(userDto);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("user10");
    }

    @Test
    void testUserDtoEmail() throws Exception {
        JsonContent<UserDto> result = json.write(userDto);
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("us1e0r@mail.ru");
    }
}
