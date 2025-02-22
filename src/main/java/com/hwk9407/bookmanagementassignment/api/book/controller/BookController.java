package com.hwk9407.bookmanagementassignment.api.book.controller;

import com.hwk9407.bookmanagementassignment.api.book.dto.request.AddBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.UpdateBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.AddBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveAllBooksResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<Void> addBook(@Valid @RequestBody AddBookRequest req) {
        AddBookResponse res = bookService.addBook(req);
        return ResponseEntity
                .created(URI.create(String.valueOf(res.id())))
                .build();
    }

    @GetMapping("/books")
    public ResponseEntity<RetrieveAllBooksResponse> retrieveAllBooks () { // todo: 페이지네이션 적용 필요
        RetrieveAllBooksResponse res = bookService.retrieveAllBooks();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<RetrieveBookResponse> retrieveBook (@PathVariable Long id) {
        RetrieveBookResponse res = bookService.retrieveBook(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PatchMapping("/books/{id}")
    public ResponseEntity<Void> updateBook (@PathVariable Long id, @Valid @RequestBody UpdateBookRequest req) {
        bookService.updateBook(id, req);
        return ResponseEntity
                .ok()
                .build();
    }
}
