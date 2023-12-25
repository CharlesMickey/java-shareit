package ru.practicum.shareit.bookingTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.status.BookingStatus;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookingRepoTests {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    private User user;
    private Item item;
    private User booker;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .name("Owner")
                .email("owner@example.com")
                .build());

        item = itemRepository.save(Item.builder()
                .name("Item")
                .description("Description")
                .available(true)
                .owner(user)
                .build());

        booker = userRepository.save(User.builder()
                .name("Booker")
                .email("booker@example.com")
                .build());
    }



    @Test
    void shouldFindBookingByBookingIdAndOwnerId() {
        Booking savedBooking = bookingRepository.save(Booking.builder()
                .start(LocalDateTime.now().plusHours(20))
                .end(LocalDateTime.now().plusHours(21))
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build());

        assertThat(bookingRepository.findBookingByBookingIdAndOwnerId(savedBooking.getId(), user.getId()))
                .isPresent();
    }

    @Test
    void shouldFindBookingByBookingIdAndOwnerIdOrOwnerItemId() {
        Booking savedBooking = bookingRepository.save(Booking.builder()
                .start(LocalDateTime.now().plusHours(13))
                .end(LocalDateTime.now().plusHours(14))
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build());

        assertThat(bookingRepository.findBookingByBookingIdAndOwnerIdOrOwnerItemId(savedBooking.getId(), user.getId()))
                .isPresent();
    }

    @Test
    void shouldFindUserBookingsWithState() {
        Booking savedBooking = bookingRepository.save(Booking.builder()
                .start(LocalDateTime.now().plusHours(11))
                .end(LocalDateTime.now().plusHours(12))
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build());

        Page<Booking> bookings = bookingRepository.findUserBookingsWithState(booker.getId(), "ALL",
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "start")));

        assertThat(bookings.getContent()).hasSize(1);
    }

    @Test
    void shouldFindOwnerBookingsWithState() {
        Booking savedBooking = bookingRepository.save(Booking.builder()
                .start(LocalDateTime.now().plusHours(1))
                .end(LocalDateTime.now().plusHours(12))
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build());

        Page<Booking> bookings = bookingRepository.findOwnerBookingsWithState(user.getId(), "ALL",
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "start")));

        assertThat(bookings.getContent()).hasSize(1);
    }

    @Test
    void shouldFindBookingByItemId() {
        Booking savedBooking = bookingRepository.save(Booking.builder()
                .start(LocalDateTime.now().plusHours(17))
                .end(LocalDateTime.now().plusHours(18))
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build());

        List<Booking> bookings = bookingRepository.findBookingByItemId(item.getId());

        assertThat(bookings).hasSize(1);
    }


    @Test
    void shouldCheckIfBookingExistsByBookerIdAndEndIsBefore() {
        Booking savedBooking = bookingRepository.save(Booking.builder()
                .start(LocalDateTime.now().plusHours(2))
                .end(LocalDateTime.now().plusHours(3))
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build());

        boolean actualValue = bookingRepository.existsByBooker_IdAndEndIsBefore(booker.getId(), LocalDateTime.now().plusHours(2));
        System.out.println("Actual value: " + actualValue);
        assertThat(actualValue).isFalse();

    }

    @Test
    void shouldFindTopBookingByItemIdAndStartBeforeOrderByStartDesc() {
        Booking savedBooking = bookingRepository.save(Booking.builder()
                .start(LocalDateTime.now().plusHours(4))
                .end(LocalDateTime.now().plusHours(5))
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build());

        Booking topBooking = bookingRepository.findTopByItemIdAndStartBeforeOrderByStartDesc(item.getId(),
                LocalDateTime.now().plusHours(6)).orElse(null);

        assertThat(topBooking).isNotNull();
        assertThat(topBooking.getStart()).isBefore(LocalDateTime.now().plusHours(6));
    }

    @Test
    void shouldFindTopBookingByItemIdAndStatusNotAndStartAfterOrderByStartAsc() {
        LocalDateTime start = LocalDateTime.now().plusHours(1);
        LocalDateTime end = LocalDateTime.now().plusHours(2);
        Booking savedBooking = bookingRepository.save(Booking.builder()
                .start(start)
                .end(end)
                .item(item)
                .booker(booker)
                .status(BookingStatus.APPROVED)
                .build());

        Booking topBooking = bookingRepository.findTopByItemIdAndStatusNotAndStartAfterOrderByStartAsc(item.getId(),
                BookingStatus.REJECTED, LocalDateTime.now()).orElse(null);

        assertThat(topBooking).isNotNull();
        assertThat(topBooking.getStart()).isAfter(LocalDateTime.now());
    }
}
