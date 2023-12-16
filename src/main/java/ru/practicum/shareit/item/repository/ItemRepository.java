package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(" select i from Item i " +
            "where i.available = true " +
            "and (upper(i.name) like upper(concat('%', ?1, '%')) " +
            "or upper(i.description) like upper(concat('%', ?1, '%')))")
    Page<Item> search(String text, Pageable pageable);

    Page<Item> findAllItemsByOwnerId(Long ownerId, Pageable pageable);

    Optional<Item> findItemByIdAndOwnerId(Long itemId, Long ownerId);

    Optional<Item> findByIdAndOwnerIdNot(Long itemId, Long ownerId);

    List<Item> findAllItemsByRequestId(Long requestId);
}
