package posmy.interview.boot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import posmy.interview.boot._enum.BookStatus;
import posmy.interview.boot.entity.Book;
import posmy.interview.boot.repository.BookRepository;
import posmy.interview.boot.request.CreateBookRequest;
import posmy.interview.boot.request.UpdateBookRequest;
import posmy.interview.boot.response.BookResponse;
import posmy.interview.boot.util.CommonMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserDetailsService userDetailsService;

    private final CommonMessage commonMessage;

    @Transactional
    public String add(CreateBookRequest req){
        Book book = Book.builder()
                    .title(req.getTitle())
                    .shelfLocation(req.getShelfLocation())
                    .status(BookStatus.AVAILABLE)
                    .createdDate(new Date())
                    .createdBy(SecurityContextHolder.getContext().getAuthentication().getName())
                    .build();
        bookRepository.save(book);
        return commonMessage.BOOK_CREATE_SUCCESS;
    }

    @Transactional
    public String update(UpdateBookRequest req){
        if(req.getId() != null){
            Optional<Book> bookOptional = bookRepository.findById(req.getId());
            if(bookOptional.isPresent()){
                Book book = bookOptional.get();
                book.setTitle(req.getTitle());
                book.setShelfLocation(req.getShelfLocation());
                if( req.getStatus() != null) book.setStatus(BookStatus.valueOf(req.getStatus()));
                bookRepository.save(book);
                return commonMessage.BOOK_UPDATE_SUCCESS;
            }
            return commonMessage.BOOK_IS_NOT_FOUND;
        }
        return commonMessage.BOOK_ID_NOT_FOUND;
    }

    @Transactional
    public String deleteById(Integer id){
        bookRepository.deleteById(id);
        return commonMessage.BOOK_DELETE_SUCCESS;
    }

    public List<BookResponse> showAll(String title){
        List<BookResponse> result = new ArrayList<BookResponse>();
        List<Book> bookList = new ArrayList<Book>();
        bookList = (title == null) ? bookRepository.findAll() : bookRepository.findByTitleContains(title);
        for(Book bk : bookList){
            result.add(BookResponse.builder()
                    .id(bk.getId())
                    .title(bk.getTitle())
                    .status(bk.getStatus().name())
                    .build());
        }
        return result;
    }

    public String borrowById(Integer bookId){
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            if(book.getStatus().equals(BookStatus.AVAILABLE)){
                book.setStatus(BookStatus.BORROWED);
                book.setLastBorrowedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                book.setLastBorrowedDate(new Date());
                bookRepository.save(book);
                return commonMessage.BOOK_BORROWED_SUCCESS;
            }else{
                return commonMessage.BOOK_IS_NOT_AVAILABLE;
            }
        }
        return commonMessage.BOOK_IS_NOT_FOUND;
    }

    public String returnById(Integer bookId){
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();
            if(book.getStatus().equals(BookStatus.BORROWED)){
                book.setStatus(BookStatus.AVAILABLE);
                book.setLastReturnedDate(new Date());
                bookRepository.save(book);
                return commonMessage.BOOK_RETURNED_SUCCESS;
            }else{
                return commonMessage.BOOK_IS_STILL_AVAILABLE;
            }
        }
        return commonMessage.BOOK_IS_NOT_FOUND;
    }
}
