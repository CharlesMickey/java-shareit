package ru.practicum.shareit.user.service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.storage.UserDaoStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDaoStorage storageDao;

    public List<UserDto> getListUsers() {
        return storageDao.getListItems().stream().map(i -> UserMapper.toUserDto(i)).collect(Collectors.toList());
    }

    public UserDto createUser(User user) {
        if (user.getName().isBlank()) {
            user.setName(user.getEmail());
        }
        List<User> userList = storageDao.getListItems();

        for (User existingUser : userList) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                throw new ConflictException("Пользователь с таким email уже существует");
            }
        }

        return UserMapper.toUserDto(storageDao.createItem(user));
    }

    public UserDto updateUser(Integer id, UserDto user) {
        User oldUser = storageDao
                .findItemById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        List<User> userList = storageDao.getListItems();

        for (User existingUser : userList) {
            if (
                    existingUser.getEmail().equals(user.getEmail()) &&
                            existingUser.getId() != id) {
                throw new ConflictException("Пользователь с таким email уже существует");
            }
        }

        User newUser = new User(
                id,
                user.getName() == null ||
                        user.getName().isBlank()
                        ? oldUser.getName()
                        : user.getName(),
                user.getEmail() == null ||
                        user.getEmail().isBlank()
                        ? oldUser.getEmail()
                        : user.getEmail()
        );
        return UserMapper.toUserDto(storageDao.updateItem(newUser));
    }

    public UserDto getUserById(Integer id) {
        User user = storageDao
                .findItemById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return UserMapper.toUserDto(user);
    }

    public void deleteUser(Integer id) {
        storageDao
                .findItemById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        storageDao.deleteItem(id);
    }
}
