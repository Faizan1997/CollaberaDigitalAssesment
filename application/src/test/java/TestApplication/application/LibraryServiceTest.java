package TestApplication.application;

import exception.ResourceNotFoundException;
import models.Book;
import models.BookStatus;
import models.Borrower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import repositories.BookRepository;
import repositories.BorrowerRepository;
import services.LibraryService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LibraryServiceTest {

    @Mock
    @Autowired
    private BorrowerRepository borrowerRepository;

    @Mock
    @Autowired
    private BookRepository bookRepository;

    @InjectMocks
    @Autowired
    private LibraryService libraryService;

    private Borrower borrower;
    private Book book;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("Faizan");
        borrower.setEmail("faizan@gmail.com");

        book = new Book();
        book.setId(1L);
        book.setIsbn("1234567890");
        book.setTitle("Math");
        book.setAuthor("Faizan");
        book.setStatus(BookStatus.AVAILABLE);
    }

    @Test
    public void testRegisterBorrower() {
        when(borrowerRepository.save(any(Borrower.class))).thenReturn(borrower);
        Borrower savedBorrower = libraryService.registerBorrower(borrower);
        assertThat(savedBorrower.getId()).isEqualTo(1L);
        verify(borrowerRepository, times(1)).save(borrower);
    }

    @Test
    public void testRegisterBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        Book savedBook = libraryService.registerBook(book);
        assertThat(savedBook.getId()).isEqualTo(1L);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testBorrowBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(borrowerRepository.findById(1L)).thenReturn(Optional.of(borrower));
        Book borrowedBook = libraryService.borrowBook(1L, 1L).orElseThrow();
        assertThat(borrowedBook.getStatus()).isEqualTo(BookStatus.BORROWED);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testBorrowBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> libraryService.borrowBook(1L, 1L));
    }

    @Test
    public void testReturnBook() {
        book.setStatus(BookStatus.BORROWED);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Book returnedBook = libraryService.returnBook(1L).orElseThrow();
        assertThat(returnedBook.getStatus()).isEqualTo(BookStatus.AVAILABLE);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    public void testReturnBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> libraryService.returnBook(1L));
    }
}
