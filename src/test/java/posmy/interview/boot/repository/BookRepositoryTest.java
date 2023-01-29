package posmy.interview.boot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import posmy.interview.boot._enum.BookStatus;
import posmy.interview.boot._enum.Role;
import posmy.interview.boot.entity.Book;
import posmy.interview.boot.entity.User;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void BookRepository_Save_ReturnBook(){
        Book book = Book.builder()
                .title("test1")
                .shelfLocation("test1")
                .status(BookStatus.AVAILABLE)
                .build();

        Book savedBook = bookRepository.save(book);

        Assertions.assertNotNull(savedBook);
        Assertions.assertTrue(savedBook.getId() > 0);
    }

    @Test
    public void BookRepository_FindAll_ReturnMoreThanOneBook(){
        Book book1 = Book.builder()
                .title("test1")
                .shelfLocation("test1")
                .status(BookStatus.AVAILABLE)
                .build();
        Book book2 = Book.builder()
                .title("test2")
                .shelfLocation("test2")
                .status(BookStatus.AVAILABLE)
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> list = bookRepository.findAll();

        Assertions.assertNotNull(list);
        Assertions.assertTrue(list.size() >= 2);
    }

    @Test
    public void BookRepository_FindById_ReturnBook(){
        Book book = Book.builder()
                .title("test1")
                .shelfLocation("test1")
                .status(BookStatus.AVAILABLE)
                .build();

        Book savedBook = bookRepository.save(book);

        Optional<Book> bookOptional = bookRepository.findById(savedBook.getId());

        Assertions.assertNotNull(bookOptional);
    }

    @Test
    public void BookRepository_UpdateBook_ReturnBookNotNull(){
        var bk = Book.builder()
                .title("test1")
                .shelfLocation("test1")
                .status(BookStatus.AVAILABLE)
                .build();

        Book savedBook = bookRepository.save(bk);
        Optional<Book> userOptional = bookRepository.findById(savedBook.getId());
        Book book = userOptional.get();
        book.setTitle("test2");
        Book updatedBook = bookRepository.save(book);
        Assertions.assertNotNull(updatedBook.getTitle());
    }

    @Test
    public void BookRepository_DeleteBook_ReturnBookIsEmpty(){
        var bk = Book.builder()
                .title("test1")
                .shelfLocation("test1")
                .status(BookStatus.AVAILABLE)
                .build();

        Book savedBook = bookRepository.save(bk);
        bookRepository.deleteById(savedBook.getId());
        Optional<Book> bookOptional = bookRepository.findById(savedBook.getId());

        Assertions.assertTrue(bookOptional.isEmpty());
    }
}
