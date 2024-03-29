package ru.practicum.shareit.item.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingNextLastDto;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.customPageRequest.CustomPageRequest;
import ru.practicum.shareit.exception.BadRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.comment.CommentDto;
import ru.practicum.shareit.item.comment.CommentMapper;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemWithBookingsDateDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ItemMapper itemMapper;
    private final BookingMapper bookingMapper;
    private final RequestRepository requestRepository;

    public ItemWithBookingsDateDto getItemById(Long itemId, Long userId) {
        Item item = itemRepository
                .findById(itemId)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена"));

        userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));


        List<CommentDto> commentDtos = CommentMapper.toCommentDto(commentRepository.findAllCommentByItemId(itemId));
        BookingNextLastDto lastBooking = bookingMapper.toBookingLastNextDto(getLatestBooking(item.getId()));
        BookingNextLastDto nextBooking = bookingMapper.toBookingLastNextDto(getNextBooking(item.getId()));
        Long itemOwnerId = item.getOwner().getId();

        return itemMapper.toItemWithBookingDto(item, userId.equals(itemOwnerId)
                ? lastBooking : null, userId.equals(itemOwnerId) ? nextBooking : null, commentDtos);

    }

    public List<ItemWithBookingsDateDto> getAllItemsByOwnerId(Long id, Integer from, Integer size) {
        if (size <= 0 || from < 0) {
            throw new BadRequestException("Неверные параметры пагинации");
        }

        userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Pageable pageable = CustomPageRequest.customOf(from, size);

        List<Item> items = itemRepository.findAllItemsByOwnerIdOrderByIdAsc(id, pageable).getContent();
        List<ItemWithBookingsDateDto> result = new ArrayList<>();

        for (Item item : items) {
            BookingNextLastDto lastBooking = bookingMapper.toBookingLastNextDto(getLatestBooking(item.getId()));
            BookingNextLastDto nextBooking = bookingMapper.toBookingLastNextDto(getNextBooking(item.getId()));
            List<CommentDto> commentDtos = CommentMapper.toCommentDto(commentRepository
                    .findAllCommentByItemId(item.getId()));

            ItemWithBookingsDateDto itemWithBookingsDateDto = itemMapper.toItemWithBookingDto(item,
                    lastBooking, nextBooking, commentDtos);
            result.add(itemWithBookingsDateDto);
        }

        return result;
    }

    private Booking getLatestBooking(Long itemId) {
        return bookingRepository.findTopByItemIdAndStartBeforeOrderByStartDesc(itemId, LocalDateTime.now())
                .orElse(null);
    }

    private Booking getNextBooking(Long itemId) {
        return bookingRepository.findTopByItemIdAndStatusNotAndStartAfterOrderByStartAsc(
                        itemId,
                        BookingStatus.REJECTED,
                        LocalDateTime.now())
                .orElse(null);
    }


    public List<ItemDto> searchItems(String text, Integer from, Integer size) {
        if (size <= 0 || from < 0) {
            throw new BadRequestException("Неверные параметры пагинации");
        }

        Pageable pageable = CustomPageRequest.customOf(from, size);

        return itemMapper.toItemDto(itemRepository.search(text, pageable).getContent());

    }

    public ItemDto createItem(Long id, ItemDto itemDto) {
        User owner = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        if (itemDto.getRequestId() != null) {
            ItemRequest itemRequest = requestRepository.findById(itemDto.getRequestId())
                    .orElseThrow(() -> new NotFoundException("Запрос не найден"));

            itemDto.setRequest(itemRequest);

        }

        Item item = itemMapper.toItem(owner, itemDto);

        itemRepository.save(item);

        return itemMapper.toItemDto(item);
    }

    public ItemDto updateItem(Long idItem, Long idOwner, ItemDto itemDto) {
        userRepository
                .findById(idOwner)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemRepository
                .findItemByIdAndOwnerId(idItem, idOwner)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена или ошибка доступа"));

        item.setAvailable(
                itemDto.getAvailable() == null
                        ? item.getAvailable()
                        : itemDto.getAvailable()
        );
        item.setDescription(
                itemDto.getDescription() == null ||
                        itemDto.getDescription().isBlank()
                        ? item.getDescription()
                        : itemDto.getDescription());
        item.setName(
                itemDto.getName() == null ||
                        itemDto.getName().isBlank()
                        ? item.getName()
                        : itemDto.getName()
        );


        return itemMapper.toItemDto(item);
    }

    public CommentDto createComment(Long userId, Long idItem, CommentDto commentDto) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemRepository
                .findById(idItem)
                .orElseThrow(() -> new NotFoundException("Вещь не найдена или ошибка доступа"));

        if (!bookingRepository.existsByBooker_IdAndEndIsBefore(userId, LocalDateTime.now())) {
            throw new BadRequestException("Нельзя оставлять комментарии если не пользовались вещью");
        }
        commentDto.setAuthor(user);
        commentDto.setItem(item);

        return CommentMapper.toCommentDto(commentRepository.save(CommentMapper.toComment(commentDto)));

    }
}
