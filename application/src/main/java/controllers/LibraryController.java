package controllers;

import models.Book;
import models.Borrower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/api/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/borrowers")
    public ResponseEntity<Borrower> registerBorrower(@RequestBody Borrower borrower) {
        return ResponseEntity.ok(libraryService.registerBorrower(borrower));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> registerBook(@RequestBody Book book) {
        return ResponseEntity.ok(libraryService.registerBook(book));
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(libraryService.getAllBooks());
    }

    @PostMapping("/borrow/{borrowerId}/books/{bookId}")
    public ResponseEntity<Book> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        return libraryService.borrowBook(borrowerId, bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/return/books/{bookId}")
    public ResponseEntity<Book> returnBook(@PathVariable Long bookId) {
        return libraryService.returnBook(bookId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
