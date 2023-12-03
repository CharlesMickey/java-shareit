package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "requests", schema = "public")
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor

public class ItemRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "requestor_id")
    private User requestor;

//    @Column
//    private LocalDate created;
}
