package ru.practicum.shareit.usersTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerUnitTests {
    @Autowired
    private UserController userController;

    private final UserMapper userMapper  = Mappers.getMapper(UserMapper.class);

    private UserDto userDto;
    private User user;


    @BeforeEach
    void init() {
        user = new User();
        user.setName("11");
        user.setEmail("1141414141@email.com");


        userDto = new UserDto();
        userDto.setName("11");
        userDto.setEmail("11@email.com");

    }

    @Test
    void createTest() throws Throwable {
        UserDto userDto = userController.createUser(user);
        assertEquals(userDto.getId(), userController.getUserById(userDto.getId()).getId());
    }

    @Test
    void userMapperTest() {
        UserDto userAfterMapp = userMapper.toUserDto(user);
        assertEquals(userAfterMapp.getEmail(), user.getEmail());
    }

    @Test
    void updateTest() throws Throwable {

        user.setEmail("1werwer@er.ru");
        userController.createUser(user);
        UserDto userDto = new UserDto();
        userDto.setName("2 name");
        userDto.setEmail("2@email.com");
        userController.updateUser(1L, userDto);
        assertEquals(userDto.getEmail(), userController.getUserById(1L).getEmail());
    }

    @Test
    void updateByWrongUserTest() {
        assertThrows(NotFoundException.class, () -> userController.updateUser(145L, userDto));
    }

    @Test
    void deleteTest() throws Throwable {
        user.setEmail("1egwev@er.ru");
        UserDto userDto = userController.createUser(user);
        assertEquals(13, userController.getListUsers().size());
        userController.deleteUser(user.getId());
        assertEquals(12, userController.getListUsers().size());
    }

    @Test
    void getByWrongIdTest() {
        assertThrows(NotFoundException.class, () -> userController.getUserById(11408L));
    }
}
