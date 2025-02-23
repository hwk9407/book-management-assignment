package com.hwk9407.bookmanagementassignment.domain.book;

 import com.hwk9407.bookmanagementassignment.domain.author.Author;
 import org.springframework.data.jpa.repository.EntityGraph;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;

 import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

    boolean existsByAuthor(Author author);

    @EntityGraph(attributePaths = {"author"})
    @Query("SELECT b FROM Book b")
    List<Book> findAllWithAuthors();
}
