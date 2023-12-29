package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.exception.UnsupportedStatusException;

public enum BookingStatus {
    // Все
    ALL,
    // Текущие
    CURRENT,
    // Будущие
    FUTURE,
    // Завершенные
    PAST,
    // Отклоненные
    REJECTED,
    // Ожидающие подтверждения
    WAITING,
    APPROVED,
    CANCELED;


    public static BookingStatus fromString(String value) {
        for (BookingStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new UnsupportedStatusException("Unknown state: " + value);
    }
}
