package ru.practicum.shareit.usersTests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testToUserDto() {
        User user = new User();
        user.setId(1L);
        user.setName("testUser");

        UserDto userDto = userMapper.toUserDto(user);

        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getName(), userDto.getName());
    }

    @Test
    public void testToUserDtoList() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("user2");

        List<UserDto> userDtoList = userMapper.toUserDto(Arrays.asList(user1, user2));

        assertNotNull(userDtoList);
        assertEquals(2, userDtoList.size());

        assertEquals(user1.getId(), userDtoList.get(0).getId());
        assertEquals(user1.getName(), userDtoList.get(0).getName());

        assertEquals(user2.getId(), userDtoList.get(1).getId());
        assertEquals(user2.getName(), userDtoList.get(1).getName());
    }
}
