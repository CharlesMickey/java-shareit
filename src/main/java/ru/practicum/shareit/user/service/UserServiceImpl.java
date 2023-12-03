package ru.practicum.shareit.user.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public List<UserDto> getListUsers() {
        List<User> users = repository.findAll();

        return UserMapper.toUserDto(users);
    }

    public UserDto createUser(User user) {

        return UserMapper.toUserDto(repository.save(user));
    }

    public UserDto updateUser(Long id, UserDto user) {
        User oldUser = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

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
        return UserMapper.toUserDto(repository.save(newUser));
    }

    public UserDto getUserById(Long id) {
        User user = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        return UserMapper.toUserDto(user);
    }

    public void deleteUser(Long id) {
        repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        repository.deleteById(id);
    }
}
