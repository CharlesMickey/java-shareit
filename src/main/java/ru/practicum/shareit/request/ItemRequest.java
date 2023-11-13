package ru.practicum.shareit.request;

import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

/**
 * TODO Sprint add-item-requests.
 */
public class ItemRequest {

    private int id;

    private String description;

    private User requestor;

    private LocalDate created;
}
