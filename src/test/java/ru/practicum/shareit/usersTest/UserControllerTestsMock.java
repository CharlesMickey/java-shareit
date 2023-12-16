package ru.practicum.shareit.usersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import ru.practicum.shareit.user.controller.UserController;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTestsMock {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    private UserDto userDto;

    @BeforeEach
    void init() {
        userDto = new UserDto();
        userDto.setId(14L);
        userDto.setName("user name");
        userDto.setEmail("user@email.com");
    }

    @Test
    void getAllTest() throws Exception {
        when(userService.getListUsers())
                .thenReturn(List.of(userDto));
        mvc.perform(get("/users")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 14L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(userDto))));
    }

    @Test
    void getByIdTest() throws Exception {
        when(userService.getUserById(anyLong()))
                .thenReturn(userDto);
        mvc.perform(get("/users/14")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 14L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userDto)));
    }

    @Test
    void createTest() throws Exception {
        when(userService.createUser(any()))
                .thenReturn(userDto);
        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 14L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(userDto)));
    }

    @Test
    void updateTest() throws Exception {
        when(userService.updateUser(anyLong(), any()))
                .thenReturn(userDto);
        mvc.perform(patch("/users/14")
                        .content(mapper.writeValueAsString(userDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Sharer-User-Id", 14L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userDto)));
    }
}
