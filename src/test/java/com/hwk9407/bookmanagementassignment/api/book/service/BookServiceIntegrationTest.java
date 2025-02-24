package com.hwk9407.bookmanagementassignment.api.book.service;

import com.hwk9407.bookmanagementassignment.api.book.dto.request.AddBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.RetrieveAllBooksRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.request.UpdateBookRequest;
import com.hwk9407.bookmanagementassignment.api.book.dto.response.RetrieveAllBooksResponse;
import com.hwk9407.bookmanagementassignment.api.book.validator.IsbnValidator;
import com.hwk9407.bookmanagementassignment.config.JpaConfig;
import com.hwk9407.bookmanagementassignment.domain.author.Author;
import com.hwk9407.bookmanagementassignment.domain.author.AuthorRepository;
import com.hwk9407.bookmanagementassignment.domain.book.Book;
import com.hwk9407.bookmanagementassignment.domain.book.BookRepository;
import com.hwk9407.bookmanagementassignment.exception.InvalidIsbnException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@DataJpaTest
@Import({BookService.class, JpaConfig.class})
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    // @MockBean 은 Spring boot 3.4.0 에서 Deprecated 됨!
    @MockitoBean
    private IsbnValidator isbnValidator;

    private Author savedAuthor;
    private Book savedBook;

    @BeforeEach
    void setUp() {
        savedAuthor = authorRepository.save(Author.builder()
                .name("테스트 저자 001")
                .email("test001@example.com")
                .build()
        );
        savedBook = bookRepository.save(Book.builder()
                .title("테스트 도서 001")
                .isbn("1234567890")
                .author(savedAuthor)
                .publicationDate(LocalDate.of(2025, 2, 25))
                .build()
        );
    }

    @Test
    @DisplayName("존재하지 않는 책 조회 시 EntityNotFoundException 예외 발생")
    public void retrieveNonExistentBookExceptionTest() {
        // given
        Long nonExistentBookId = 999L;

        // when & then
        assertThatThrownBy(() -> bookService.retrieveBook(nonExistentBookId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("조회되지 않는 책 ID 입니다.");
    }

    @Test
    @DisplayName("올바른 값이 들어와서 페이지네이션과 필터링, 정렬이 정상적으로 작동하는지 테스트")
    public void paginationSuccessTest() {
        // given
        for (int i = 1; i <= 15; i++) {
            bookRepository.save(Book.builder()
                    .title("테스트 도서 " + i)
                    .isbn("1234567890" + i)
                    .author(savedAuthor)
                    .publicationDate(LocalDate.of(2025, 1, i))
                    .build());
        }
        RetrieveAllBooksRequest req = new RetrieveAllBooksRequest(2, 5, "ASC", "title", null, LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 9));

        // when
        RetrieveAllBooksResponse res = bookService.retrieveAllBooks(req);

        // then
        assertThat(res.contents()).hasSize(3);
        assertThat(res.totalPage()).isEqualTo(2);
        assertThat(res.contents().get(0).title()).isEqualTo("테스트 도서 7");
        assertThat(res.contents().get(1).title()).isEqualTo("테스트 도서 8");
        assertThat(res.contents().get(2).title()).isEqualTo("테스트 도서 9");
    }

    @Test
    @DisplayName("중복된 ISBN으로 도서를 추가하면 InvalidIsbnException 예외 발생")
    public void addBookDuplicateIsbnExceptionTest() {
        // given
        AddBookRequest req = new AddBookRequest(
                "새로운 도서",
                "설명",
                "1234567890",
                null,
                savedAuthor.getId()
        );
        doNothing().when(isbnValidator).validate(anyString());

        // when & then
        assertThatThrownBy(() -> bookService.addBook(req))
                .isInstanceOf(InvalidIsbnException.class)
                .hasMessageContaining("Isbn이 이미 존재합니다.");
    }

    @Test
    @DisplayName("도서 수정 시 존재하지 않는 저자 ID로 변경하면 EntityNotFoundException 예외 발생")
    public void updateBookNonExistentAuthorExceptionTest() {
        // given
        UpdateBookRequest req = new UpdateBookRequest(
                "수정된 제목",
                "수정된 설명",
                "1111111110",
                null,
                999L
        );
        doNothing().when(isbnValidator).validate(anyString());

        // when & then
        assertThatThrownBy(() -> bookService.updateBook(savedBook.getId(), req))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("존재하지 않는 저자 ID 입니다.");
    }

    @Test
    @DisplayName("도서 삭제 시 정상적으로 삭제됨")
    public void deleteBookSuccessTest() {
        // when
        bookService.deleteBook(savedBook.getId());

        // then
        assertThat(bookRepository.existsById(savedBook.getId())).isFalse();
    }
}