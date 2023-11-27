package ru.practicum.shareit.user.service;

import java.util.List;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;


public interface UserService {

    List<UserDto> getListUsers();

    UserDto createUser(User user);

    UserDto updateUser(Integer id, UserDto user);

    UserDto getUserById(Integer id);

    void deleteUser(Integer id);
}
