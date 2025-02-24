package com.hwk9407.bookmanagementassignment.domain.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface BookRepositoryQuery {

    Page<Book> retrieveBooksWithFilter(Long authorId, LocalDate startPubDate, LocalDate endPubDate, Pageable pageable);
}
