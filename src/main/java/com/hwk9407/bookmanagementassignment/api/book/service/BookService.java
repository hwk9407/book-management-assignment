package com.hwk9407.bookmanagementassignment.api.book.service;

import com.hwk9407.bookmanagementassignment.api.book.dto.request.AddBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.AddBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveAllBooksResponse;
import com.hwk9407.bookmanagementassignment.domain.author.Author;
import com.hwk9407.bookmanagementassignment.domain.author.AuthorRepository;
import com.hwk9407.bookmanagementassignment.domain.book.Book;
import com.hwk9407.bookmanagementassignment.domain.book.BookRepository;
import com.hwk9407.bookmanagementassignment.domain.book.IsbnValidator;
import com.hwk9407.bookmanagementassignment.exception.InvalidIsbnException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    public RetrieveAllBooksResponse retrieveAllBooks() {
        // 모든 도서를 조회해서 Book Entity들을 List에 저장
        // 저장된 Entity -> Response로 변환 후 반환
        return null;
    }
}
