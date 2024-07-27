package TestApplication.application;

import controllers.LibraryController;
import exception.GlobalExceptionHandler;
import models.Book;
import models.BookStatus;
import models.Borrower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import services.LibraryService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.util.Optional;

public class LibraryControllerTest {

    private MockMvc mockMvc;

    @Mock
    @Autowired
    private LibraryService libraryService;

    @InjectMocks
    @Autowired
    private LibraryController libraryController;

    private Borrower borrower;
    private Book book;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(libraryController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        borrower = new Borrower();
        borrower.setId(1L);
        borrower.setName("John Doe");
        borrower.setEmail("john.doe@example.com");

        book = new Book();
        book.setId(1L);
        book.setIsbn("1234567890");
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setStatus(BookStatus.AVAILABLE);
    }

    @Test
    public void testRegisterBorrower() throws Exception {
        when(libraryService.registerBorrower(borrower)).thenReturn(borrower);

        mockMvc.perform(post("/api/library/borrowers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Faizan\",\"email\":\"faizan@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Faizan"))
                .andExpect(jsonPath("$.email").value("faizan@gmail.com"));
    }

    @Test
    public void testRegisterBook() throws Exception {
        when(libraryService.registerBook(book)).thenReturn(book);

        mockMvc.perform(post("/api/library/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"isbn\":\"1234567890\",\"title\":\"Math\",\"author\":\"Faizan\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.isbn").value("1234567890"))
                .andExpect(jsonPath("$.title").value("Math"))
                .andExpect(jsonPath("$.author").value("Faizan"));
    }

    @Test
    public void testGetAllBooks() throws Exception {
        when(libraryService.getAllBooks()).thenReturn(Collections.singletonList(book));

        mockMvc.perform(get("/api/library/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].isbn").value("1234567890"))
                .andExpect(jsonPath("$[0].title").value("Math"))
                .andExpect(jsonPath("$[0].author").value("Faizan"));
    }

    @Test
    public void testBorrowBook() throws Exception {
        when(libraryService.borrowBook(1L, 1L)).thenReturn(Optional.of(book));

        mockMvc.perform(post("/api/library/borrow/1/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("BORROWED"));
    }

    @Test
    public void testReturnBook() throws Exception {
        when(libraryService.returnBook(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(post("/api/library/return/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }
}

