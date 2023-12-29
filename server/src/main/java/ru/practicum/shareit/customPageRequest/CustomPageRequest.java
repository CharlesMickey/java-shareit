package ru.practicum.shareit.customPageRequest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPageRequest extends PageRequest {

    public CustomPageRequest(int from, int size) {
        super(calculatePageNumber(from, size), size,  Sort.unsorted());
    }

    public CustomPageRequest(int from, int size, Sort sort) {
        super(calculatePageNumber(from, size), size, sort);
    }

    private static int calculatePageNumber(int from, int size) {
        return (int) Math.floor((double) from / size);
    }

    public static Pageable customOf(int from, int size) {
        return PageRequest.of(calculatePageNumber(from, size), size);
    }

    public static Pageable customOf(int from, int size, Sort sort) {
        return PageRequest.of(calculatePageNumber(from, size), size, sort);
    }
}
