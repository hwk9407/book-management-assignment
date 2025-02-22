package com.hwk9407.bookmanagementassignment.api.author.service;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.AddAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.AddAuthorResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAllAuthorsResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAuthorResponse;
import com.hwk9407.bookmanagementassignment.domain.author.Author;
import com.hwk9407.bookmanagementassignment.domain.author.AuthorRepository;
import com.hwk9407.bookmanagementassignment.exception.EmailAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public AddAuthorResponse addAuthor(AddAuthorRequest req) {
        if (authorRepository.existsByEmail(req.email())) {
            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
        Author author = authorRepository.save(Author.builder()
                .name(req.name())
                .email(req.email())
                .build());
        return new AddAuthorResponse(author.getId());
    }

    @Transactional(readOnly = true)
    public RetrieveAllAuthorsResponse retrieveAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return RetrieveAllAuthorsResponse.from(authors);
    }

    @Transactional(readOnly = true)
    public RetrieveAuthorResponse retrieveAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("조회되지 않는 저자 ID 입니다.")
        );
        return RetrieveAuthorResponse.from(author);
    }
}
