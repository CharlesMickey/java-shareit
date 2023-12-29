package ru.practicum.shareit.user.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.validated.Create;
import ru.practicum.shareit.validated.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserDto> getListUsers() {
        List<UserDto> usersList = userService.getListUsers();
        log.info("Get request /users, data transmitted: {}", usersList);
        return usersList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(@Validated(Create.class) @RequestBody User user) {
        log.info("Post request /users, data transmitted: {}", user);
        return userService.createUser(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public UserDto updateUser(
            @PathVariable Long id,
            @Validated(Update.class) @RequestBody UserDto user) throws Throwable {
        log.info("Patch request /users, data transmitted: {}", user);
        return userService.updateUser(id, user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) throws Throwable {
        log.info("Get request /users/{id}", id);
        return userService.getUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws Throwable {
        log.info("Delete request /users/{id}", id);

        userService.deleteUser(id);
    }
}
