package com.hwk9407.bookmanagementassignment.api.book.service;

import com.hwk9407.bookmanagementassignment.api.book.dto.request.AddBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.RetrieveAllBooksRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.UpdateBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.AddBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveAllBooksResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveBookResponse;
import com.hwk9407.bookmanagementassignment.domain.author.Author;
import com.hwk9407.bookmanagementassignment.domain.author.AuthorRepository;
import com.hwk9407.bookmanagementassignment.domain.book.Book;
import com.hwk9407.bookmanagementassignment.domain.book.BookRepository;
import com.hwk9407.bookmanagementassignment.api.book.validator.IsbnValidator;
import com.hwk9407.bookmanagementassignment.exception.InvalidIsbnException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final IsbnValidator isbnValidator;
    private final AuthorRepository authorRepository;

    @Transactional
    public AddBookResponse addBook(@Valid AddBookRequest req) {
        isbnValidator.validate(req.isbn());
        if (bookRepository.existsByIsbn(req.isbn())) {
            throw new InvalidIsbnException("Isbn이 이미 존재합니다.");
        }
        Author author = authorRepository.findById(req.authorId())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 저자 ID 입니다."));
        Book book = bookRepository.save(Book.builder()
                .title(req.title())
                .description(req.description())
                .isbn(req.isbn())
                .publicationDate(req.publicationDate())
                .author(author)
                .build());
        return new AddBookResponse(book.getId());
    }

    @Transactional(readOnly = true)
    public RetrieveAllBooksResponse retrieveAllBooks(RetrieveAllBooksRequest req) {
        Sort.Direction direction = "ASC".equalsIgnoreCase(req.orderBy()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, req.sortBy());
        Pageable pageable = PageRequest.of(req.page() - 1, req.size(), sort);
        Page<Book> books = bookRepository.retrieveBooksWithFilter(req.authorId(), req.startPubDate(), req.endPubDate(), pageable);

        return RetrieveAllBooksResponse.from(books);
    }

    @Transactional(readOnly = true)
    public RetrieveBookResponse retrieveBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("조회되지 않는 책 ID 입니다.")
        );
        return RetrieveBookResponse.from(book);
    }

    @Transactional
    public void updateBook(Long id, UpdateBookRequest req) {
        if (req.isbn() != null) {
            isbnValidator.validate(req.isbn());
        }
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("조회되지 않는 책 ID 입니다.")
        );
        Author author = (req.authorId() != null) ? authorRepository.findById(req.authorId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 저자 ID 입니다.")
        ) : null;
        book.update(
                req.title(),
                req.description(),
                req.isbn(),
                req.publicationDate(),
                author
        );
    }

    public void deleteBook(Long id) {
        bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("조회되지 않는 책 ID 입니다.")
        );
        bookRepository.deleteById(id);
    }
}
