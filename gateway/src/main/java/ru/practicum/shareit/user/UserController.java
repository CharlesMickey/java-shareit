package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.validated.Create;
import ru.practicum.shareit.validated.Update;


@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserClient userClient;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<Object> getListUsers() {

        log.info("Get request /users, data transmitted");
        return userClient.getListUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Validated(Create.class) UserDto user) {
        log.info("Post request /users, data transmitted: {}", user);
        return userClient.createUser(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(
            @PathVariable Long id,
            @Validated(Update.class) @RequestBody UserDto user) throws Throwable {
        log.info("Patch request /users, data transmitted: {}", user);
        return userClient.updateUser(id, user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) throws Throwable {
        log.info("Get request /users/{id}", id);
        return userClient.getUserById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) throws Throwable {
        log.info("Delete request /users/{id}", id);

        userClient.deleteUser(id);
    }
}
