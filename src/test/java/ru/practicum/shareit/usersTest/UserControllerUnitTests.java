package ru.practicum.shareit.usersTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerUnitTests {
    @Autowired
    private UserController userController;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void init() {
        user = new User();
        user.setName("11");
        user.setEmail("11@email.com");


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
    void updateTest() throws Throwable {
        userController.createUser(user);
        UserDto userDto = new UserDto();
        userDto.setName("2 name");
        userDto.setEmail("2@email.com");
        userController.updateUser(1L, userDto);
        assertEquals(userDto.getEmail(), userController.getUserById(1L).getEmail());
    }

    @Test
    void updateByWrongUserTest()  {
        assertThrows(NotFoundException.class, () -> userController.updateUser(145L, userDto));
    }

    @Test
    void deleteTest() throws Throwable {
        user.setEmail("1@er.ru");
        UserDto userDto = userController.createUser(user);
        assertEquals(3, userController.getListUsers().size());
        userController.deleteUser(user.getId());
        assertEquals(2, userController.getListUsers().size());
    }

    @Test
    void getByWrongIdTest() {
        assertThrows(NotFoundException.class, () -> userController.getUserById(1L));
    }
}
