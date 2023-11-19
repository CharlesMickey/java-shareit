package ru.practicum.shareit.user.service;

import java.util.List;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;


public interface UserService {


    public List<UserDto> getListUsers();

    public UserDto createUser(User user);

    public UserDto updateUser(Integer id, UserDto user);

    public UserDto getUserById(Integer id);

    public void deleteUser(Integer id);
}
