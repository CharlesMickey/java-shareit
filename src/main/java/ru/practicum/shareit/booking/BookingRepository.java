package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b join fetch b.item i where b.id = :bookingId and i.owner.id = :ownerId")
    Optional<Booking> findBookingByBookingIdAndOwnerId(@Param("bookingId") Long bookingId,
                                                       @Param("ownerId") Long ownerId);

    @Query("select b from Booking b " +
            "join fetch b.item i " +
            "where b.id = :bookingId and (i.owner.id = :ownerId or b.booker.id  = :ownerId)")
    Optional<Booking> findBookingByBookingIdAndOwnerIdOrOwnerItemId(@Param("bookingId") Long bookingId,
                                                                    @Param("ownerId") Long ownerId);


    @Query("select b from Booking b " +
            "join fetch b.item i " +
            "where b.booker.id = :userId " +
            "and (:state = 'ALL' or " +
            "(:state = 'CURRENT' and b.start <= current_timestamp and b.end >= current_timestamp) or " +
            "(:state = 'PAST' and b.end < current_timestamp) or " +
            "(:state = 'FUTURE' and b.start > current_timestamp) or " +
            "(:state = 'WAITING' and b.status = 'WAITING') or " +
            "(:state = 'REJECTED' and b.status = 'REJECTED')) " +
            "order by b.start desc")
    List<Booking> findUserBookingsWithState(@Param("userId") Long userId, @Param("state") String state);

    @Query("select b from Booking b " +
            "join fetch b.item i " +
            "where i.owner.id = :ownerId " +
            "and (:state = 'ALL' or " +
            "(:state = 'CURRENT' and b.start <= current_timestamp and b.end >= current_timestamp) or " +
            "(:state = 'PAST' and b.end < current_timestamp) or " +
            "(:state = 'FUTURE' and b.start > current_timestamp) or " +
            "(:state = 'WAITING' and b.status = 'WAITING') or " +
            "(:state = 'REJECTED' and b.status = 'REJECTED')) " +
            "order by b.start desc")
    List<Booking> findOwnerBookingsWithState(@Param("ownerId") Long ownerId, @Param("state") String state);


    @Query("select b from Booking b " +
            "join fetch b.item i " +
            "where i.id = :itemId " +
            "order by b.start desc")
    List<Booking> findBookingByItemId(@Param("itemId") Long itemId);

    boolean existsByBooker_IdAndEndIsBefore(Long bookerId, LocalDateTime end);
}
