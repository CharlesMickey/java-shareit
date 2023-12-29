package ru.practicum.shareit.bookingTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingController;

import ru.practicum.shareit.booking.BookingDto;
import ru.practicum.shareit.booking.BookingNextLastDto;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.constants.HttpConstants;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@WebMvcTest(controllers = BookingController.class)
@SpringBootTest(properties = "db.name=test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingControllerTestsMock {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mvc;

    private UserDto userDto;

    private ItemDto itemDto;

    private BookingDto bookingDto;

    private BookingNextLastDto bookingNextLastDto;

    @BeforeEach
    void init() {
        userDto = UserDto
                .builder()
                .id(2L)
                .name("username")
                .email("user@email.ru")
                .build();

        itemDto = ItemDto
                .builder()
                .id(1L)
                .name("itemname")
                .description("descriptionitem")
                .available(true)
                .build();

        bookingDto = BookingDto
                .builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 12, 12, 10, 0))
                .end(LocalDateTime.of(2023, 12, 20, 10, 0))
                .booker(User.builder().id(userDto.getId()).name(userDto.getName()).build())
                .item(Item.builder().id(itemDto.getId()).name(itemDto.getName()).build())
                .build();

        bookingNextLastDto = BookingNextLastDto
                .builder()
                .id(1L)
                .start(LocalDateTime.of(2023, 12, 20, 10, 0))
                .end(LocalDateTime.of(2023, 12, 20, 11, 0))
                .itemId(1L)
                .build();
    }

    @Test
    void createBookingTest() throws Exception {
        when(bookingService.createBooking(any(), any()))
                .thenReturn(bookingDto);
        mvc.perform(post("/bookings")
                        .content(mapper.writeValueAsString(bookingNextLastDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpConstants.X_SHARER_USER_ID, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
    }

    @Test
    void approvingBookingTest() throws Exception {
        bookingDto.setStatus(BookingStatus.APPROVED);
        when(bookingService.approvingBooking(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(bookingDto);
        mvc.perform(patch("/bookings/1?approved=true")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpConstants.X_SHARER_USER_ID, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
    }

    @Test
    void findOwnerBookingsWithStateTest() throws Exception {
        when(bookingService.findOwnerBookingsWithState(anyLong(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));
        mvc.perform(get("/bookings/owner")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpConstants.X_SHARER_USER_ID, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookingDto))));
    }

    @Test
    void findUserBookingsWithStateTest() throws Exception {
        when(bookingService.findUserBookingsWithState(anyLong(), any(), anyInt(), anyInt()))
                .thenReturn(List.of(bookingDto));
        mvc.perform(get("/bookings")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpConstants.X_SHARER_USER_ID, 2L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookingDto))));
    }

    @Test
    void getBookingByIdTest() throws Exception {
        when(bookingService.getBookingById(anyLong(), anyLong()))
                .thenReturn(bookingDto);
        mvc.perform(get("/bookings/1")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpConstants.X_SHARER_USER_ID, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
    }

    @Test
    void getAllByUserWrongStateTest() throws Exception {
        when(bookingService.findUserBookingsWithState(anyLong(), any(), anyInt(), anyInt()))
                .thenThrow(BadRequestException.class);
        mvc.perform(get("/bookings?state=twerxt")
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpConstants.X_SHARER_USER_ID, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}