package com.hwk9407.bookmanagementassignment.api.author.service;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.AddAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.AddAuthorResponse;
import com.hwk9407.bookmanagementassignment.domain.author.Author;
import com.hwk9407.bookmanagementassignment.domain.author.AuthorRepository;
import com.hwk9407.bookmanagementassignment.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
