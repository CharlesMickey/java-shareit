package ru.practicum.shareit.request;

import java.time.LocalDate;

import ru.practicum.shareit.user.model.User;


public class ItemRequest {

    private int id;

    private String description;

    private User requestor;

    private LocalDate created;
}
