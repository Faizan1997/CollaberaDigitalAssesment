package models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;
    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookStatus status = BookStatus.AVAILABLE;
}



