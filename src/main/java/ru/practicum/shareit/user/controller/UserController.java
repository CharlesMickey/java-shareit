package ru.practicum.shareit.user.controller;

import java.util.List;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getListUsers() {
        List<User> usersList = userService.getListUsers();
        log.info("Get request /users, data transmitted: {}", usersList);
        return usersList;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("Post equest /users, data transmitted: {}", user);
        return userService.createUser(user);
    }

    @PatchMapping("/{id}")
    public User updateUser(
            @PathVariable Integer id,
            @RequestBody UserDto user) throws Throwable {
        log.info("Patch request /users, data transmitted: {}", user);
        return userService.updateUser(id, user);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) throws Throwable {
        log.info("Get request /users/{id}", id);
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) throws Throwable {
        log.info("Delete request /users/{id}", id);

        userService.deleteUser(id);
    }
}
