package com.hwk9407.bookmanagementassignment.api.author.controller;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.AddAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.AddAuthorResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAllAuthorsResponse;
import com.hwk9407.bookmanagementassignment.api.author.service.AuthorService;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveAllBooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/authors")
    public ResponseEntity<Void> addAuthor(@RequestBody AddAuthorRequest req) {
        AddAuthorResponse res = authorService.addAuthor(req);
        return ResponseEntity
                .created(URI.create(String.valueOf(res.id())))
                .build();
    }

    @GetMapping("/authors")
    public ResponseEntity<RetrieveAllAuthorsResponse> retrieveAllAuthors() { // todo: 페이지네이션 적용 필요
        RetrieveAllAuthorsResponse res = authorService.retrieveAllAuthors();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(res);
    }
}
