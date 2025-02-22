package com.hwk9407.bookmanagementassignment.api.book.service;

import com.hwk9407.bookmanagementassignment.api.book.dto.request.AddBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.AddBookResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveAllBooksResponse;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveBookResponse;
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

import java.util.List;

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
    public RetrieveAllBooksResponse retrieveAllBooks() {
        List<Book> books = bookRepository.findAll();
        return RetrieveAllBooksResponse.from(books);
    }

    public RetrieveBookResponse retrieveBook(Long id) {
        // 해당 아이디로 되어있는 책 DB에서 조회
        // 없을 시 EntityNotFound 예외 발생
        // Entity -> Response Dto로 변환 후 반환
        return null;
    }
}
