package posmy.interview.boot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import posmy.interview.boot.request.CreateBookRequest;
import posmy.interview.boot.request.UpdateBookRequest;
import posmy.interview.boot.response.BookResponse;
import posmy.interview.boot.service.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<String> createBook(@Validated @RequestBody CreateBookRequest request){
        return ResponseEntity.ok(bookService.add(request));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateBook(@Validated @RequestBody UpdateBookRequest request){
        return ResponseEntity.ok(bookService.update(request));
    }

    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer bookId){
        return ResponseEntity.ok(bookService.deleteById(bookId));
    }

    @GetMapping("/user/showAll")
    @ResponseBody
    public ResponseEntity<List<BookResponse>> showAll(@RequestParam(required = false) String title){
        return ResponseEntity.ok(bookService.showAll(title));
    }

    @PutMapping("/user/borrow/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Integer bookId){
        return ResponseEntity.ok(bookService.borrowById(bookId));
    }

    @PutMapping("/user/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Integer bookId){
        return ResponseEntity.ok(bookService.returnById(bookId));
    }
}
