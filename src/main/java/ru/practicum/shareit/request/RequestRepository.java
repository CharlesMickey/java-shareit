package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<ItemRequest, Long> {

    @Query("select ir from ItemRequest ir " +
            "left join Item i ON ir.id = i.request.id " +
            "where ir.requestor.id = :userId " +
            "order by ir.created DESC")
    List<ItemRequest> getAllItemRequestsByOwnerId(@Param("userId") Long userId);
}
