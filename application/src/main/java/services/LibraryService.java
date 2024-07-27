package services;

import models.Book;
import models.BookStatus;
import models.Borrower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.BookRepository;
import repositories.BorrowerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    @Autowired
    private BorrowerRepository borrowerRepository;

    @Autowired
    private BookRepository bookRepository;

    public Borrower registerBorrower(Borrower borrower) {
        return borrowerRepository.save(borrower);
    }

    public Book registerBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> borrowBook(Long borrowerId, Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);
        Optional<Borrower> borrowerOpt = borrowerRepository.findById(borrowerId);

        if (bookOpt.isPresent() && borrowerOpt.isPresent()) {
            Book book = bookOpt.get();
            if (book.getStatus() == BookStatus.AVAILABLE) {
                book.setStatus(BookStatus.BORROWED);
                bookRepository.save(book);
                return Optional.of(book);
            }
        }

        return Optional.empty();
    }

    public Optional<Book> returnBook(Long bookId) {
        Optional<Book> bookOpt = bookRepository.findById(bookId);

        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setStatus(BookStatus.AVAILABLE);
            bookRepository.save(book);
            return Optional.of(book);
        }

        return Optional.empty();
    }
}

