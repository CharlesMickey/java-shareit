package ru.practicum.shareit.itemsTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.comment.CommentRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class ItemRepoTests {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository itemRequestRepository;

    @Autowired
    private CommentRepository commentRepository;

    @AfterEach
    void tearDown() {
        itemRepository.deleteAll();
        itemRequestRepository.deleteAll();
        userRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    void searchTest() {
        User user = userRepository.save(User.builder().name("name").email("email@email.com").build());
        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(user);
        itemRepository.save(item);
        List<Item> items = itemRepository.search("ipti", PageRequest.of(0, 10)).getContent();
        assertThat(items.size(), equalTo(1));
    }

    @Test
    void findAllByOwnerIdTest() {
        User user = userRepository.save(User.builder().name("name").email("email@email.com").build());
        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(user);
        itemRepository.save(item);
        List<Item> items = itemRepository.findAllItemsByOwnerIdOrderByIdAsc(user.getId(), PageRequest.of(0, 10))
                .getContent();
        assertThat(items.size(), equalTo(1));
    }

    @Test
    void findAllByRequestIdTest() {
        User user = userRepository.save(User.builder().name("name").email("email@email.com").build());
        User user2 = userRepository.save(User.builder().name("name2").email("email2@email.com").build());
        ItemRequest itemRequestData = new ItemRequest();
        itemRequestData.setDescription("description");
        itemRequestData.setRequestor(user2);
        itemRequestData.setCreated(LocalDateTime.now());

        ItemRequest itemRequest = itemRequestRepository.save(itemRequestData);
        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item.setAvailable(true);
        item.setOwner(user);
        item.setRequest(itemRequest);
        itemRepository.save(item);
        assertThat(itemRepository.findAllItemsByRequestId(itemRequest.getId()).size(), equalTo(1));
    }

    @Test
    void findAllCommentByItemIdTest() {
        User user = userRepository.save(User.builder().name("name").email("email@email.com").build());
        User user2 = userRepository.save(User.builder().name("name2").email("email2@email.com").build());

        Item itemData = new Item();
        itemData.setName("name");
        itemData.setDescription("description");
        itemData.setAvailable(true);
        itemData.setOwner(user);


        Item item = itemRepository.save(itemData);

        Comment comment = new Comment();
        comment.setText("text");
        comment.setItem(item);
        comment.setAuthor(user2);
        comment.setCreated(LocalDateTime.now());
        commentRepository.save(comment);
        List<Comment> comments = commentRepository.findAllCommentByItemId(item.getId());
        System.out.println("Comments found: " + comments.size());
        comments.forEach(comment1 -> System.out.println("Comment text: " + comment1.getText()));

        assertThat(comments.size(), equalTo(1));
    }
}
