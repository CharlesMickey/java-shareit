package ru.practicum.shareit.usersTest;

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

    @Test
    void testUserDto() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(10L);
        userDto.setName("user10");
        userDto.setEmail("us1e0r@mail.ru");


        JsonContent<UserDto> result = json.write(userDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(10);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("user10");
        assertThat(result).extractingJsonPathStringValue("$.email").isEqualTo("us1e0r@mail.ru");
    }
}