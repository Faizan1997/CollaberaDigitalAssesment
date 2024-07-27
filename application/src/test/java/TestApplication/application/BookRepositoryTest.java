package TestApplication.application;

import models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import repositories.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234567890");
        book.setTitle("Math");
        book.setAuthor("Faizan");

        Book savedBook = bookRepository.save(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("1234567890");
        assertThat(savedBook.getTitle()).isEqualTo("Math");
        assertThat(savedBook.getAuthor()).isEqualTo("Faizan");
    }
}

