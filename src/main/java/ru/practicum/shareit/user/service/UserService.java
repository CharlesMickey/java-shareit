package ru.practicum.shareit.user.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.storage.UserDaoStorage;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDaoStorage<User> storageDao;

    public List<User> getListUsers() {
        return storageDao.getListItems();
    }

    public User createUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getEmail());
        }
        List<User> userList = storageDao.getListItems();

        for (User existingUser : userList) {
            if (existingUser.getEmail().equals(user.getEmail())) {
                throw new ConflictException("Пользователь с таким email уже существует");
            }
        }

        return (User) storageDao.createItem(user);
    }

    public User updateUser(Integer id, UserDto user) throws Throwable {
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
                        user.getName().isBlank() ||
                        user.getName().isEmpty()
                        ? oldUser.getName()
                        : user.getName(),
                user.getEmail() == null ||
                        user.getEmail().isBlank() ||
                        user.getEmail().isEmpty()
                        ? oldUser.getEmail()
                        : user.getEmail()
        );
        return (User) storageDao.updateItem(newUser);
    }

    public User getUserById(Integer id) throws Throwable {
        return (User) storageDao
                .findItemById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public void deleteUser(Integer id) throws Throwable {
        storageDao
                .findItemById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        storageDao.deleteItem(id);
    }
}
