package com.hwk9407.bookmanagementassignment.domain.book;

 import com.hwk9407.bookmanagementassignment.domain.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByAuthor(Author author);
}
