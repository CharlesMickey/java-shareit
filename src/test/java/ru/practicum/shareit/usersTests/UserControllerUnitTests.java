package ru.practicum.shareit.usersTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerUnitTests {
    @Autowired
    private UserController userController;

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
    @DisplayName("Create User")
    void createTest() throws Throwable {
        UserDto createdUser = userController.createUser(user);
        assertEquals(createdUser.getId(), userController.getUserById(createdUser.getId()).getId());
    }

    @Test
    @DisplayName("Update User")
    void updateTest() throws Throwable {
        user.setEmail("1werwer@er.ru");
        UserDto createdUser = userController.createUser(user);

        UserDto updateUserDto = new UserDto();
        updateUserDto.setName("2 name");
        updateUserDto.setEmail("2@email.com");

        userController.updateUser(createdUser.getId(), updateUserDto);
        assertEquals(updateUserDto.getEmail(), userController.getUserById(createdUser.getId()).getEmail());
    }

    @Test
    @DisplayName("Update User with Wrong ID")
    void updateByWrongUserTest() {
        assertThrows(NotFoundException.class, () -> userController.updateUser(145L, userDto));
    }

    @Test
    @DisplayName("Delete User")
    void deleteTest() throws Throwable {
        user.setEmail("1egwev@er.ru");
        UserDto createdUser = userController.createUser(user);
        assertEquals(1, userController.getListUsers().size());
        userController.deleteUser(createdUser.getId());
        assertEquals(0, userController.getListUsers().size());
    }

    @Test
    @DisplayName("Get User By Wrong ID")
    void getByWrongIdTest() {
        assertThrows(NotFoundException.class, () -> userController.getUserById(11408L));
    }
}
