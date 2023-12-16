package ru.practicum.shareit.itemRequestTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class ItemRequestsRepoTests {
    @Autowired
    private RequestRepository itemRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        itemRequestRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAllItemRequestsByOwnerIdTest() {
        User user = userRepository.save(User.builder().name("na13me").email("em123ail@email.com").build());
        itemRequestRepository.save(ItemRequest.builder().description("description").requestor(user)
                .created(LocalDateTime.now()).build());
        List<ItemRequest> items = itemRequestRepository.getAllItemRequestsByOwnerId(user.getId());
        assertThat(items.size(), equalTo(1));
    }

    @Test
    void findAllRequestsByRequestorIdNotTest() {
        User user = userRepository.save(User.builder().name("na3me").email("em213ail@email.com").build());
        itemRequestRepository.save(ItemRequest.builder().description("descri123ption").requestor(user)
                .created(LocalDateTime.now()).build());
        assertThat(itemRequestRepository.findAllRequestsByRequestorIdNot(user.getId(),
                        PageRequest.of(0, 10))
                .stream().count(), equalTo(0L));
        User user2 = userRepository.save(User.builder().name("na3me").email("ew33f3213ail@email.com").build());
        assertThat(itemRequestRepository.findAllRequestsByRequestorIdNot(user2.getId(),
                        PageRequest.of(0, 10))
                .stream().count(), equalTo(1L));
    }
}



