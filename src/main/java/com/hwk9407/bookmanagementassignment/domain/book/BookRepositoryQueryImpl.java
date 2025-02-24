package com.hwk9407.bookmanagementassignment.domain.book;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BookRepositoryQueryImpl implements BookRepositoryQuery {

    private final JPAQueryFactory queryFactory;
    QBook book = QBook.book;

    @Override
    public Page<Book> retrieveBooksWithFilter(Long authorId, LocalDate startPubDate, LocalDate endPubDate, Pageable pageable) {

        BooleanBuilder whereClause = new BooleanBuilder();
        if (authorId != null) {
            whereClause.and(book.author.id.eq(authorId));
        }
        if (startPubDate != null) {
            whereClause.and(book.publicationDate.goe(startPubDate));
        }
        if (endPubDate != null) {
            whereClause.and(book.publicationDate.loe(endPubDate));
        }
        JPAQuery<Book> query = queryFactory.selectFrom(book)
                .fetchJoin()
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        List<Book> books = query.fetch();
        Long total = Optional.ofNullable(
                queryFactory.select(book.count())
                .from(book)
                .where(whereClause)
                .fetchOne()
        ).orElse(0L);
        return new PageImpl<>(books, pageable, total);
    }
}
