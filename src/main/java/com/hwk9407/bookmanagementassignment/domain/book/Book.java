package com.hwk9407.bookmanagementassignment.domain.book;

import com.hwk9407.bookmanagementassignment.domain.author.Author;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column
    private LocalDate publicationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Builder
    public Book(String title, String description, String isbn, LocalDate publicationDate, Author author) {
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.author = author;
    }

    public void update(String title, String description, String isbn, LocalDate publicationDate, Author author) {
        this.title = Objects.requireNonNullElse(title, this.title);
        this.description = Objects.requireNonNullElse(description, this.description);
        this.isbn = Objects.requireNonNullElse(isbn, this.isbn);
        this.publicationDate = Objects.requireNonNullElse(publicationDate, this.publicationDate);
        this.author = Objects.requireNonNullElse(author, this.author);
    }
}
