package com.hwk9407.bookmanagementassignment.api.author.service;

import com.hwk9407.bookmanagementassignment.api.author.dto.request.RetrieveAllAuthorsRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.request.UpdateAuthorRequest;
import com.hwk9407.bookmanagementassignment.api.author.dto.response.RetrieveAllAuthorsResponse;
import com.hwk9407.bookmanagementassignment.config.JpaConfig;
import com.hwk9407.bookmanagementassignment.domain.author.Author;
import com.hwk9407.bookmanagementassignment.domain.author.AuthorRepository;
import com.hwk9407.bookmanagementassignment.domain.book.Book;
import com.hwk9407.bookmanagementassignment.domain.book.BookRepository;
import com.hwk9407.bookmanagementassignment.exception.CannotDeleteAuthorException;
import com.hwk9407.bookmanagementassignment.exception.EmailAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({AuthorService.class, JpaConfig.class})
class AuthorServiceIntegrationTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    private Author savedAuthor;

    @BeforeEach
    void setUp() {
        savedAuthor = authorRepository.save(Author.builder()
                .name("테스트 저자 001")
                .email("test001@example.com")
                .build()
        );
    }

    @Test
    @DisplayName("존재하지 않는 ID 조회 시 EntityNotFoundException 발생")
    public void nonExistentAuthorExceptionTest() {
        // given
        Long nonExistentId = 999L;

        // when & then
        assertThatThrownBy(() -> authorService.retrieveAuthor(nonExistentId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("조회되지 않는 저자 ID 입니다.");
    }

    @Test
    @DisplayName("올바른 값이 잘 들어와서 페이지네이션이 잘 작동하는지 테스트")
    public void paginationSuccessTest() {
        // given
        for (int i = 1; i <= 15; i++) {
            authorRepository.save(Author.builder()
                    .name("저자" + i)
                    .email("author" + i + "@example.com")
                    .build());
        }
        RetrieveAllAuthorsRequest req = new RetrieveAllAuthorsRequest(2, 10);

        // when
        RetrieveAllAuthorsResponse res = authorService.retrieveAllAuthors(req);

        // then
        assertThat(res.contents()).hasSize(6);
        assertThat(res.totalPage()).isEqualTo(2);
    }

    @Test
    @DisplayName("이메일 변경 시 이미 존재하는 이메일이면 EmailAlreadyExistsException 예외 발생")
    public void updateAuthorDuplicateEmailExceptionTest1() {
        // given
        authorRepository.save(Author.builder()
                .name("다른 저자")
                .email("duplicate@example.com")
                .build());
        UpdateAuthorRequest req = new UpdateAuthorRequest(null, "duplicate@example.com");

        // when & then
        assertThatThrownBy(() -> authorService.updateAuthor(savedAuthor.getId(), req))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("이미 존재하는 이메일입니다.");
    }

    @Test
    @DisplayName("본인의 이메일로 수정하는 경우 정상 작동 테스트")
    public void updateAuthorDuplicateEmailExceptionTest2() {
        // given
        UpdateAuthorRequest req = new UpdateAuthorRequest(null, "test@example.com");

        // when & then
        assertThatCode(() -> authorService.updateAuthor(savedAuthor.getId(), req))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("삭제 시 연관된 책이 있으면 삭제 불가능 CannotDeleteAuthorException 예외 발생")
    public void deleteAuthorExceptionTest() {
        // given
        bookRepository.save(Book.builder()
                .title("테스트 도서")
                .isbn("1234567890")
                .author(savedAuthor)
                .build());

        // when & then
        assertThatThrownBy(() -> authorService.deleteAuthor(savedAuthor.getId()))
                .isInstanceOf(CannotDeleteAuthorException.class)
                .hasMessageContaining("연관된 책이 존재하여 저자를 삭제할 수 없습니다.");
    }

    @Test
    @DisplayName("연관 도서가 없는 저자는 정상 삭제됨")
    public void deleteAuthorWithoutBooksShouldSucceed() {
        // when
        authorService.deleteAuthor(savedAuthor.getId());

        // then
        assertThat(authorRepository.existsById(savedAuthor.getId())).isFalse();
    }
}