package ru.practicum.shareit.status;

public enum BookingStatus {
    WAITING,
    APPROVED,
    REJECTED,
    CANCELED, CURRENT, ALL, PAST, FUTURE;


    public static BookingStatus fromString(String value) {
        for (BookingStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown BookingStatus: " + value);
    }
}
