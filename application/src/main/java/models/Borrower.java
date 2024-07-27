package models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
}
