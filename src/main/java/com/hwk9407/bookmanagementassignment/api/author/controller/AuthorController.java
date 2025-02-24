package com.hwk9407.bookmanagementassignment.api.author.controller;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.AddAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.request.RetrieveAllAuthorsRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.request.UpdateAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.AddAuthorResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAllAuthorsResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAuthorResponse;
import com.hwk9407.bookmanagementassignment.api.author.service.AuthorService;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.RetrieveAllBooksRequest;
import com.hwk9407.bookmanagementassignment.util.DtoValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;
    private final DtoValidator dtoValidator;

    @PostMapping("/authors")
    public ResponseEntity<Void> addAuthor(@Valid @RequestBody AddAuthorRequest req) {
        AddAuthorResponse res = authorService.addAuthor(req);
        return ResponseEntity
                .created(URI.create(String.valueOf(res.id())))
                .build();
    }

    @GetMapping("/authors")
    public ResponseEntity<RetrieveAllAuthorsResponse> retrieveAllAuthors(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        RetrieveAllAuthorsRequest req = new RetrieveAllAuthorsRequest(page, size);
        dtoValidator.validate(req);
        RetrieveAllAuthorsResponse res = authorService.retrieveAllAuthors(req);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<RetrieveAuthorResponse> retrieveAuthor (@PathVariable Long id) {
        RetrieveAuthorResponse res = authorService.retrieveAuthor(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<Void> updateAuthor (@PathVariable Long id, @Valid @RequestBody UpdateAuthorRequest req) {
        authorService.updateAuthor(id, req);
        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<Void> deleteAuthor (@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
