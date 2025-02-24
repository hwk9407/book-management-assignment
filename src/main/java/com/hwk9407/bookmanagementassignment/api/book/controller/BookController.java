package com.hwk9407.bookmanagementassignment.api.book.controller;

import com.hwk9407.bookmanagementassignment.api.book.dto.request.AddBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.RetrieveAllBooksRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.UpdateBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.AddBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveAllBooksResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.service.BookService;
import com.hwk9407.bookmanagementassignment.util.DtoValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final DtoValidator dtoValidator;

    @PostMapping("/books")
    public ResponseEntity<Void> addBook(@Valid @RequestBody AddBookRequest req) {
        AddBookResponse res = bookService.addBook(req);
        return ResponseEntity
                .created(URI.create(String.valueOf(res.id())))
                .build();
    }

    @GetMapping("/books")
    public ResponseEntity<RetrieveAllBooksResponse> retrieveAllBooks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "DESC") String orderBy,
            @RequestParam(defaultValue = "publicationDate") String sortBy,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) LocalDate startPubDate,
            @RequestParam(required = false) LocalDate endPubDate
    ) {
        RetrieveAllBooksRequest req = new RetrieveAllBooksRequest(page, size, orderBy, sortBy, authorId, startPubDate, endPubDate);
        dtoValidator.validate(req);
        if (startPubDate != null && endPubDate != null) {
            if (startPubDate.isAfter(endPubDate)) {
                throw new IllegalArgumentException("종료일을 시작일보다 이전으로 조회할 수 없습니다.");
            }
        }
        RetrieveAllBooksResponse res = bookService.retrieveAllBooks(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<RetrieveBookResponse> retrieveBook(@PathVariable Long id) {
        RetrieveBookResponse res = bookService.retrieveBook(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<Void> updateBook(@PathVariable Long id, @Valid @RequestBody UpdateBookRequest req) {
        bookService.updateBook(id, req);
        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
