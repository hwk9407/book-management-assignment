package com.hwk9407.bookmanagementassignment.api.author.service;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.AddAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.request.RetrieveAllAuthorsRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.request.UpdateAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.AddAuthorResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAllAuthorsResponse;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAuthorResponse;
import com.hwk9407.bookmanagementassignment.domain.author.Author;
import com.hwk9407.bookmanagementassignment.domain.author.AuthorRepository;
import com.hwk9407.bookmanagementassignment.domain.book.BookRepository;
import com.hwk9407.bookmanagementassignment.exception.CannotDeleteAuthorException;
import com.hwk9407.bookmanagementassignment.exception.EmailAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

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
    public RetrieveAllAuthorsResponse retrieveAllAuthors(RetrieveAllAuthorsRequest req) {
        Pageable pageable = PageRequest.of(req.page() - 1, req.size());
        Page<Author> authors = authorRepository.findAll(pageable);
        return RetrieveAllAuthorsResponse.from(authors);
    }

    @Transactional(readOnly = true)
    public RetrieveAuthorResponse retrieveAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("조회되지 않는 저자 ID 입니다.")
        );
        return RetrieveAuthorResponse.from(author);
    }

    @Transactional
    public void updateAuthor(Long id, @Valid UpdateAuthorRequest req) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("조회되지 않는 저자 ID 입니다.")
        );
        if (req.email() != null && !author.getEmail().equals(req.email()) && authorRepository.existsByEmail(req.email())) {
            throw new EmailAlreadyExistsException("이미 존재하는 이메일입니다.");
        }
        author.update(
                req.name(),
                req.email()
        );
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("조회되지 않는 저자 ID 입니다.")
        );
        if (bookRepository.existsByAuthor(author)) {
            throw new CannotDeleteAuthorException("연관된 책이 존재하여 저자를 삭제할 수 없습니다.");
        }
        authorRepository.delete(author);
    }
}
